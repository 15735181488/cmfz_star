package com.baizhi.service.impl;

import com.baizhi.annotation.ClearRedisCache;
import com.baizhi.annotation.RedisCache;
import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.StarDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Star;
import com.baizhi.service.AlbumService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("albumService")
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private StarDao starDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @RedisCache
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        Map<String,Object> map=new HashMap<>();
        Album album=new Album();
        RowBounds rowBounds= new RowBounds((page - 1) * rows, rows);
        List<Album> albums = albumDao.selectByRowBounds(album, rowBounds);
        for (Album album1 : albums) {
            Star star = starDao.selectByPrimaryKey(album1.getStarId());
            album1.setStar(star);
        }
        int totalCounts = albumDao.selectCount(album);
        map.put("rows",albums);
        map.put("page",page);
        int totalPage=totalCounts%rows==0?totalCounts/rows:totalCounts/rows+1;
        map.put("total",totalPage);
        map.put("records",totalCounts);
        return map;
    }

    @Override
    @ClearRedisCache
    public String add(Album album) {
        album.setId(UUID.randomUUID().toString());
        album.setCount(0);
        String substring = album.getCover().substring(album.getCover().lastIndexOf("\\") + 1);
        album.setCover(substring);
        int i = albumDao.insertSelective(album);
        if(i==0){
            throw new RuntimeException("专辑添加失败");
        }
        return album.getId();
    }

    @Override
    @ClearRedisCache
    public void update(Album album) {
        if("".equals(album.getCover())){
            album.setCover(null);
        }
        try {
            albumDao.updateByPrimaryKeySelective(album);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }
    }

    @Override
    @ClearRedisCache
    public void delete(String id, HttpServletRequest request) {
        Album album = albumDao.selectByPrimaryKey(id);
        int i = albumDao.deleteByPrimaryKey(id);
        if(i==0){
            throw new RuntimeException("删除失败");
        }else{
            String cover = album.getCover();
            File file = new File(request.getServletContext().getRealPath("/album/img"), cover);
            boolean b = file.delete();
            if(b==false){
                throw new RuntimeException("专辑图片删除失败");
            }
        }
    }

    @Override
    public Album selectOne(String id) {
        return albumDao.selectByPrimaryKey(id);
    }
}
