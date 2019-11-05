package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.dao.StarDao;
import com.baizhi.entity.Star;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StarDao starDao;



    @RequestMapping("selectUsersByStarId")
    public Map<String,Object> selectUsersByStarId(Integer page,Integer rows,String starId){
        Map<String, Object> map = userService.selectUsersByStarId(page, rows, starId);
        return map;
    }
    @RequestMapping("selectAll")
    public Map<String,Object> selectAll(Integer page,Integer rows){
        Map<String, Object> map = userService.selectAll(page, rows);
        return map;
    }

    @RequestMapping("export")
    public void export(HttpServletResponse resp, HttpServletRequest request){
        List<User> list = userService.selectAll();
        for (User user : list) {
            String photo = user.getPhoto();
            String realPath = request.getServletContext().getRealPath("user/img");
            user.setPhoto(realPath+"\\"+photo);
            Star star = starDao.selectByPrimaryKey(user.getStarId());
            user.setStar(star);
        }

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("所有用户","用户"), com.baizhi.entity.User.class, list);

        String fileName = "用户报表("+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+").xls";
        //处理中文下载名乱码
        try {
            fileName = new String(fileName.getBytes("gbk"),"iso-8859-1");
            //设置 response
            resp.setContentType("application/vnd.ms-excel");
            resp.setHeader("content-disposition","attachment;filename="+fileName);
            workbook.write(resp.getOutputStream());
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("echarts")
    public Map<String,Object> echarts(){
        Integer[] nan = userService.select("男");
        Integer[] nv = userService.select("女");
        Map<String,Object> map=new HashMap<>();
        map.put("nan",nan);
        map.put("nv",nv);
        return map;
    }




}
