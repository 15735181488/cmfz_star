package com.baizhi.controller;

import com.baizhi.entity.Star;
import com.baizhi.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("star")
public class StarController {
    @Autowired
    private StarService starService;

    @RequestMapping("selectAll")
    public Map<String,Object> selectAll(Integer page,Integer rows){
        Map<String,Object> map=new HashMap<>();
        List<Star> stars = starService.selectAll(page, rows);
        int totalCounts = starService.findTotalCounts();
        map.put("rows",stars);
        map.put("page",page);
        int totalPage=totalCounts%rows==0?totalCounts/rows:totalCounts/rows+1;
        map.put("total",totalPage);
        map.put("records",totalCounts);
        return map;
    }
    @RequestMapping("edit")
    public Map<String,Object> edit(Star star, String oper, HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        try {
            if("add".equals(oper)){
                String id = starService.add(star);
                map.put("message",id);
            }
            if("edit".equals(oper)){
                starService.update(star);
            }
            if("del".equals(oper)){
                starService.delete(star.getId(),request);
            }
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
            map.put("message",e.getMessage());

        }
        return map;
    }
    @RequestMapping("upload")
    public Map<String,Object> upload(MultipartFile photo,String id,HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        try {
            photo.transferTo(new File(request.getServletContext().getRealPath("/star/img"),photo.getOriginalFilename()));
            Star star=new Star();
            star.setId(id);
            star.setPhoto(photo.getOriginalFilename());
            starService.update(star);
            map.put("status",true);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }
    @RequestMapping("findAll")
    public void findAll(HttpServletResponse response) throws IOException {
        List<Star> stars = starService.selectAll();
        StringBuilder sb = new StringBuilder();
        sb.append("<select>");
        stars.forEach(star -> {
            sb.append("<option value=").append(star.getId()).append(">").append(star.getNickname()).append("</option>");
        });
        sb.append("</select>");
        //获取响应流
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(sb.toString());

    }
}
