package com.baizhi.poi;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class testPoi {
    public static void main(String[] args) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        CellRangeAddress cellRangeAddress = new CellRangeAddress(1, 2, 0, 4);
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy-MM-dd HH:mm:ss");
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setColor(Font.COLOR_RED);
        cellStyle.setFont(font);
        cellStyle.setDataFormat(format);
        HSSFSheet sheet = workbook.createSheet("用户表");
        sheet.addMergedRegion(cellRangeAddress);
        sheet.setColumnWidth(0,25*256);
        sheet.setColumnWidth(1,25*256);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("所有学生的信息");
        HSSFCell cell1 = row.createCell(1);
        cell1.setCellValue(new Date());
        cell.setCellStyle(cellStyle);
        cell1.setCellStyle(cellStyle);
        workbook.write(new FileOutputStream(new File("D:/baizhishixun/视频+笔记+作业/后期项目/day6/视频/user.xls")));

    }
}
