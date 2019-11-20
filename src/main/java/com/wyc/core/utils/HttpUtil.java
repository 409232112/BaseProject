package com.wyc.core.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;

/**
 * Created by wangyc on 2019/11/20.
 */
public class HttpUtil {
    public static String post(String strUrl, Map<String,Object> params,Map<String,Object> headers) throws Exception{
        String result = "";

        try {
            URL url = new URL(strUrl);
            //通过调用url.openConnection()来获得一个新的URLConnection对象，并且将其结果强制转换为HttpURLConnection.
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            //设置连接的超时值为30000毫秒，超时将抛出SocketTimeoutException异常
            urlConnection.setConnectTimeout(30000);
            //设置读取的超时值为30000毫秒，超时将抛出SocketTimeoutException异常
            urlConnection.setReadTimeout(30000);
            //将url连接用于输出，这样才能使用getOutputStream()。getOutputStream()返回的输出流用于传输数据
            urlConnection.setDoOutput(true);
            //设置通用请求属性为默认浏览器编码类型
            urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            for (Map.Entry<String,Object> entry : headers.entrySet()) {
                urlConnection.setRequestProperty(entry.getKey(), String.valueOf(entry.getValue()));
            }
            //getOutputStream()返回的输出流，用于写入参数数据。
            OutputStream outputStream = urlConnection.getOutputStream();
            String content="";
            for (Map.Entry<String,Object> entry : params.entrySet()) {
                content += entry.getKey()+"="+entry.getValue()+"&";
            }
            outputStream.write(content.getBytes());
            outputStream.flush();

            //此时将调用接口方法。getInputStream()返回的输入流可以读取返回的数据。
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb=new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            result=sb.toString();
            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
