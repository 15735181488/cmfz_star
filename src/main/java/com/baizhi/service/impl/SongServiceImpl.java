package com.baizhi.service.impl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.SongDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Song;
import com.baizhi.service.SongService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("songService")
@Transactional
public class SongServiceImpl implements SongService {
    @Autowired
    private SongDao songDao;
    @Autowired
    private AlbumDao albumDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> selectSongsByAlbumId(Integer page, Integer rows, String albumId) {
        Map<String,Object> map=new HashMap<>();
        Song song=new Song();
        song.setAlbumId(albumId);
        RowBounds rowBounds=new RowBounds((page-1)*rows,rows);
        List<Song> songs = songDao.selectByRowBounds(song, rowBounds);
        for (Song song1 : songs) {
            Album album = albumDao.selectByPrimaryKey(song1.getAlbumId());
            song1.setAlbum(album);
        }
        int totalCounts = songDao.selectCount(song);
        map.put("rows",songs);
        map.put("page",page);
        int totalPage=totalCounts%rows==0?totalCounts/rows:totalCounts/rows+1;
        map.put("total",totalPage);
        map.put("records",totalCounts);
        return map;
    }

    @Override
    public String add(Song song) {
        song.setId(UUID.randomUUID().toString());
        song.setCreateDate(new Date());
        String substring = song.getName().substring(song.getName().lastIndexOf("\\") + 1);
        song.setName(substring);
        int i = songDao.insertSelective(song);
        if(i==0){
            throw new RuntimeException("音乐添加失败");
        }
        return song.getId();
    }

    @Override
    public void update(Song song) {
        int i = songDao.updateByPrimaryKeySelective(song);
        if(i==0){
            throw new RuntimeException("音乐跟新失败");
        }
    }

}
