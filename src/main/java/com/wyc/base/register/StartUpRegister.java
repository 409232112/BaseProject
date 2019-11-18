package com.wyc.base.register;

import com.wyc.base.annotation.StartUp;
import com.wyc.base.annotation.StartUpScan;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;

import org.springframework.core.io.ResourceLoader;

import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.*;

/**
 * Created by wangyc on 2019/11/18.
 */
public class StartUpRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, BeanClassLoaderAware, EnvironmentAware {

    static final Logger LOG = Logger.getLogger(StartUpRegister.class);

    private static final String CLASS_SUFFIX = ".class";

    private static final String CLASS_FILE_PREFIX = File.separator + "classes"  + File.separator;

    private static final String PACKAGE_SEPARATOR = ".";

    public static List<String> getClassList() {
        return classList;
    }

    private static List classList;

    private static void setClassList(List<String> classList) {
        StartUpRegister.classList = classList;
    }

    private ResourceLoader resourceLoader;

    private ClassLoader classLoader;

    private Environment environment;

    public void setEnvironment(Environment environment) {
        this.environment = environment;

    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;

    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        startUpPackageScan(importingClassMetadata);
        register(importingClassMetadata, registry);

    }
    private void startUpPackageScan(AnnotationMetadata metadata) {
        Map<String, Object> defaultAttrs = metadata.getAnnotationAttributes(StartUpScan.class.getName(), true);
        if (defaultAttrs != null && defaultAttrs.size() > 0) {
            LOG.info("startup package scan: " + buildPackages((String[]) defaultAttrs.get("basePackages")));
        }
    }

    private String buildPackages(String[] basePackages) {
        if (basePackages == null || basePackages.length == 0) {
            return "null";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : basePackages) {
            stringBuilder.append(s).append(",");
        }
        stringBuilder.substring(0, stringBuilder.length() - 2);
        return stringBuilder.toString();
    }
    public void register(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        scanner.setResourceLoader(this.resourceLoader);
        Set<String> basePackages;
        Map<String, Object> attrs = metadata.getAnnotationAttributes(StartUpScan.class.getName());
        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(StartUp.class);
        scanner.addIncludeFilter(annotationTypeFilter);
        basePackages = getBasePackages(metadata);


        List classList = new ArrayList();

        for (String basePackage : basePackages) {
            List<String> classnameList =getClazzName(basePackage);
            try {

                for (String classname : classnameList) {
                    // 扫描Section注解
                    StartUp s = Class.forName(classname).getAnnotation(StartUp.class);
                    if (s != null) {
                        classList.add(classname);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setClassList(classList);
    }


    protected ClassPathScanningCandidateComponentProvider getScanner() {

        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {

            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                if (beanDefinition.getMetadata().isIndependent()) {

                    if (beanDefinition.getMetadata().isInterface()
                            && beanDefinition.getMetadata().getInterfaceNames().length == 1
                            && Annotation.class.getName().equals(beanDefinition.getMetadata().getInterfaceNames()[0])) {
                        try {
                            Class<?> target = ClassUtils.forName(beanDefinition.getMetadata().getClassName(),
                                    StartUpRegister.this.classLoader);
                            return !target.isAnnotation();
                        } catch (Exception ex) {
                            this.logger.error(
                                    "Could not load target class: " + beanDefinition.getMetadata().getClassName(), ex);
                        }
                    }
                    return true;
                }
                return false;

            }
        };
    }

    protected Set<String> getBasePackages(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata
                .getAnnotationAttributes(StartUpScan.class.getCanonicalName());

        Set<String> basePackages = new HashSet<String>();
        for (String pkg : (String[]) attributes.get("basePackages")) {
            if (pkg != null && !"".equals(pkg)) {
                basePackages.add(pkg);
            }
        }

        if (basePackages.isEmpty()) {
            basePackages.add(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        }
        return basePackages;
    }


    private static List<String> getClazzName(String packageName) {
        List<String> result = new ArrayList<>();
        String suffixPath = packageName.replaceAll("\\.", "/");
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            Enumeration<URL> urls = loader.getResources(suffixPath);
            while(urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if(url != null) {
                    String protocol = url.getProtocol();
                    if("file".equals(protocol)) {
                        String path = url.getPath();
                        result.addAll(getAllClassNameByFile(new File(path)));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    private static List<String> getAllClassNameByFile(File file) {
        List<String> result =  new ArrayList<>();
        if(!file.exists()) {
            return result;
        }
        if(file.isFile()) {
            String path = file.getPath();
            // 注意：这里替换文件分割符要用replace。因为replaceAll里面的参数是正则表达式,而windows环境中File.separator="\\"的,因此会有问题
            if(path.endsWith(CLASS_SUFFIX)) {
                path = path.replace(CLASS_SUFFIX, "");
                // 从"/classes/"后面开始截取
                String clazzName = path.substring(path.indexOf(CLASS_FILE_PREFIX) + CLASS_FILE_PREFIX.length()).replace(File.separator, PACKAGE_SEPARATOR);
                if(-1 == clazzName.indexOf("$")) {
                    result.add(clazzName);
                }
            }
            return result;
        } else {
            File[] listFiles = file.listFiles();
            if(listFiles != null && listFiles.length > 0) {
                for (File f : listFiles) {
                    if(f.isFile()){
                        String path = f.getPath();
                        if(path.endsWith(CLASS_SUFFIX)) {
                            path = path.replace(CLASS_SUFFIX, "");
                            // 从"/classes/"后面开始截取
                            String clazzName = path.substring(path.indexOf(CLASS_FILE_PREFIX) + CLASS_FILE_PREFIX.length()).replace(File.separator, PACKAGE_SEPARATOR);
                            if(-1 == clazzName.indexOf("$")) {
                                result.add(clazzName);
                            }
                        }
                    }
                }
            }
            return result;
        }
    }

}
