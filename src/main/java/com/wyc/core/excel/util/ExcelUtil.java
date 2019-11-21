package com.wyc.core.excel.util;

import com.wyc.core.base.exception.BaseException;
import com.wyc.core.shiro.CurrentUserHelper;
import com.wyc.core.utils.FileUtil;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


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


    private static void setSizeColumn(Sheet sheet, int size) {
        for (int columnNum = 0; columnNum < size; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                Row currentRow;

                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }

                if (currentRow.getCell(columnNum) != null) {
                    Cell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            if (columnWidth > 255) {
                columnWidth = 254;
            }
            sheet.setColumnWidth(columnNum, columnWidth * 256);

        }
    }

    public static String createFile(List<Map<String,String>> dataList) throws Exception{

        List<String> titles = new ArrayList<>();
        for (Map.Entry<String, String> entry : dataList.get(0).entrySet()) {
            titles.add(entry.getKey());
        }

        HSSFWorkbook wb = new HSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();
        String safeName = WorkbookUtil.createSafeSheetName("sheet");
        // 创建sheet
        Sheet sheet = wb.createSheet(safeName);

        // 设置标题单元格样式
        HSSFCellStyle  titlestyle = wb.createCellStyle();

        titlestyle.setAlignment(HorizontalAlignment.CENTER); // 居中
        HSSFFont font = wb.createFont();
        font.setFontName("黑体");
        font.setBold(true);
        titlestyle.setFont(font);


        // 生成行对象
        Row row = sheet.createRow((short) 0);
        int cellNum = 0;
        for(int i=0;i<titles.size();i++){
            Cell cell = row.createCell(cellNum);
            cell.setCellValue(createHelper.createRichTextString(titles.get(i)));
            cell.setCellStyle(titlestyle);
            ++cellNum;
        }

        int rowNum = 1;

        for (int k = 0; k < dataList.size(); k++) {
            row = sheet.createRow((short) rowNum);
            int cellNo = 0;
            sheet.autoSizeColumn(rowNum);

            for (Map.Entry<String, String> entry : dataList.get(k).entrySet()) {
                Cell cell = row.createCell(cellNo);
                String data = String.valueOf(entry.getValue());
                cell.setCellValue(createHelper.createRichTextString(data));
                ++cellNo;
            }

            ++rowNum;
        }
        setSizeColumn(sheet,dataList.size());


        String fileName = dateformate.format(new Date())+".xls";
        FileOutputStream fileOut = new FileOutputStream(tempDir+fileName);
        wb.write(fileOut);
        fileOut.close();
        return fileName;
    }

}
