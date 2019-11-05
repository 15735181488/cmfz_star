package com.baizhi.poi;

import org.apache.poi.hssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestExport {
    public static void main(String[] args) throws Exception {
        List<User> list = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            User user = new User();
            user.setId(i+"");
            user.setName("张三"+i);
            user.setBir(new Date());
            list.add(user);
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy-MM-dd");
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(format);
        HSSFSheet sheet = workbook.createSheet("学生信息");
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            HSSFRow row = sheet.createRow(i);

            HSSFCell cell = row.createCell(0);
            cell.setCellValue(user.getId());

            HSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(user.getName());

            HSSFCell cell2 = row.createCell(2);
            cell2.setCellValue(user.getBir());
            cell2.setCellStyle(cellStyle);
        }
        workbook.write(new FileOutputStream(new File("D:/baizhishixun/视频+笔记+作业/后期项目/day6/视频/user.xls")));


    }
}
