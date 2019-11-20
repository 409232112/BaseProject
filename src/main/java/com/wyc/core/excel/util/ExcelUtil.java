package com.wyc.core.excel.util;

import com.wyc.core.base.exception.BaseException;
import com.wyc.core.shiro.CurrentUserHelper;
import com.wyc.core.utils.FileUtil;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangyc on 2019/11/20.
 */

@Component
public class ExcelUtil {


    private static String tempDir;

    @Value("${tempDir}")
    public void setTempDir(String tempDir) {
        this.tempDir = tempDir;
    }

    private static DateFormat dateformate = new SimpleDateFormat("yyyyMMddHHmmss");

    public static String createFile(String name,List<String> titles, List<String> columns, List<Map<String,Object>> datas) throws Exception{
        if(titles.size()!=columns.size()){
            throw new BaseException("titles 和 columns 长度不配");
        }
        List<Map> dataList = new ArrayList();
        for(int i=0;i<datas.size();i++){
            Map data = new HashMap();
            for (Map.Entry<String,Object> entry : datas.get(i).entrySet()) {
                if(columns.contains(entry.getKey())){
                    data.put(entry.getKey(),entry.getValue());
                }
            }
            dataList.add(data);
        }

        Workbook wb = new XSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();
        String safeName = WorkbookUtil.createSafeSheetName("sheet1");
        // 创建sheet
        Sheet sheet1 = wb.createSheet(safeName);

        // 设置标题单元格样式
        CellStyle titlestyle = wb.createCellStyle();
        /**
        titlestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titlestyle.setFillForegroundColor(HSSFColor.YELLOW.index);
        titlestyle.setBorderBottom(CellStyle.BORDER_THIN);
        titlestyle.setBorderTop(CellStyle.BORDER_THIN);
        titlestyle.setBorderLeft(CellStyle.BORDER_THIN);
        titlestyle.setBorderRight(CellStyle.BORDER_THIN);
        titlestyle.setAlignment(CellStyle.ALIGN_CENTER);*/

        // 生成行对象
        Row row = sheet1.createRow((short) 0);
        int cellNum = 0;
        for(int i=0;i<titles.size();i++){
            Cell cell = row.createCell(cellNum);
            cell.setCellValue(createHelper.createRichTextString(titles.get(i)));
            ++cellNum;
        }

        int rowNum = 1;

        for (int k = 0; k < dataList.size(); k++) {
            row = sheet1.createRow((short) rowNum);
            int cellNo = 0;
            for (int i = 0; i < columns.size(); i++) {
                Cell cell = row.createCell(cellNo);
                Object value = dataList.get(k).get(columns.get(i));
                String data = String.valueOf(value);
                cell.setCellValue(createHelper.createRichTextString(data));
                ++cellNo;

            }
            ++rowNum;
        }
        String fileName = dateformate.format(new Date())+"_"+ CurrentUserHelper.getName()+"_"+name+".xls";
        FileOutputStream fileOut = new FileOutputStream(tempDir+fileName);
        wb.write(fileOut);
        fileOut.close();
        return fileName;
    }

}
