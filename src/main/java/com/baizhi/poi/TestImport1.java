package com.baizhi.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestImport1 {
    public static void main(String[] args) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("D:/baizhishixun/视频+笔记+作业/后期项目/day6/视频/user.xls")));
        //一个表格里有多个sheet,要指定导出哪个sheet
        HSSFSheet sheet = workbook.getSheet("学生信息");
        List<User> list = new ArrayList<>();
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            User user = new User();
            HSSFRow row = sheet.getRow(i);

//            获取类对象
//            Class<?> userClass = Class.forName("com.baizhi.poi.User");
//            Class<? extends User> aClass = user.getClass();
//            获取user类对象
            Class<User> userClass = User.class;
//            获取user中的所有的属性,得到一个属性数组
            Field[] fields = userClass.getDeclaredFields();
//            遍历所有的属性
            for (int j = 0; j < fields.length; j++) {
//                获取到第j个属性对象
                Field field = fields[j];
//                获取到第j个属性名称 id name bir
                String fieldName = field.getName();
//                准备出set方法名 setId setName setBir
                String methodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
                if(fieldName.equals("bir")){
                    Method method = userClass.getDeclaredMethod(methodName, Date.class);
                    HSSFCell cell = row.getCell(j);
                    Date value = cell.getDateCellValue();
//                执行当前方法
//                参数1:执行当前方法的对象
//                参数2:执行该方法时传入的实参
                    method.invoke(user, value);
                }else{
//                通过方法名获取当前方法对象
//                参数1:方法名称
//                参数2:方法形参的类对象
                    Method method = userClass.getDeclaredMethod(methodName, String.class);
                    HSSFCell cell = row.getCell(j);
                    String value = cell.getStringCellValue();
//                执行当前方法
//                参数1:执行当前方法的对象
//                参数2:执行该方法时传入的实参
                    method.invoke(user, value);
                }
            }
            list.add(user);
        }

        for (User user : list) {
            System.out.println(user);
        }


    }
}
