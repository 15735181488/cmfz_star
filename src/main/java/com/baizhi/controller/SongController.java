package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Song;
import com.baizhi.service.AlbumService;
import com.baizhi.service.SongService;
import it.sauronsoftware.jave.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("song")
public class SongController {
    @Autowired
    private SongService songService;
    @Autowired
    private AlbumService albumService;

    @RequestMapping("selectSongsByAlbumId")
    public Map<String,Object> selectSongsByAlbumId(Integer page, Integer rows, String albumId){
        Map<String, Object> map = songService.selectSongsByAlbumId(page, rows, albumId);
        return map;
    }
    @RequestMapping("edit")
    public Map<String,Object> edit(Song song, String oper){
        Map<String,Object> map=new HashMap<>();
        try {
            if("add".equals(oper)){
                String id = songService.add(song);
                map.put("status",true);
                map.put("message",id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @RequestMapping("upload")
    public Map<String,Object> upload(MultipartFile name, String id,String albumId, HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        try {
            //处理文件上传
            File file = new File(request.getServletContext().getRealPath("album/music"), name.getOriginalFilename());
            name.transferTo(file);
            //修改文件名称
            Song song=new Song();
            song.setId(id);
            song.setName(name.getOriginalFilename());

            //文件大小
            BigDecimal size = new BigDecimal(name.getSize());
            BigDecimal mod = new BigDecimal(1024);
            BigDecimal realSize = size.divide(mod).divide(mod).setScale(2,BigDecimal.ROUND_HALF_UP);
            song.setSize(realSize+"MB");
            //文件时长
            Encoder encoder = new Encoder();
            long duration = encoder.getInfo(file).getDuration();
            song.setDuration(duration/1000/60+":"+duration/1000%60);
            songService.update(song);
            //修改专辑中数量
            Album album = albumService.selectOne(albumId);
            album.setCount(album.getCount()+1);
            albumService.update(album);
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",true);
        }
        return map;
    }

}
