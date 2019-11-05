package com.baizhi.service.impl;

import com.baizhi.dao.StarDao;
import com.baizhi.entity.Star;
import com.baizhi.service.StarService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Service("starService")
@Transactional
public class StarServiceImpl implements StarService {
    @Autowired
    private StarDao starDao;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Star> selectAll(Integer page, Integer rows) {
        Star star=new Star();
        RowBounds rowBounds=new RowBounds((page-1)*rows,rows);
        List<Star> stars = starDao.selectByRowBounds(star, rowBounds);
        return stars;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public int findTotalCounts() {
        Star star=new Star();
        return starDao.selectCount(star);
    }

    @Override
    public String add(Star star) {
        star.setId(UUID.randomUUID().toString());
        String substring = star.getPhoto().substring(star.getPhoto().lastIndexOf("\\") + 1);
        star.setPhoto(substring);
        int i = starDao.insertSelective(star);
        if(i==0){
            throw new RuntimeException("明星添加失败") ;
        }
        return star.getId();
    }

    @Override
    public void update(Star star) {
        if("".equals(star.getPhoto())){
            star.setPhoto(null);
        }
        try {
            starDao.updateByPrimaryKeySelective(star);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }
    }

    @Override
    public void delete(String id, HttpServletRequest request) {
        Star star=starDao.selectByPrimaryKey(id);
        int i = starDao.deleteByPrimaryKey(id);
        if(i==0){
            throw new RuntimeException("删除失败");
        }else{
            String photo = star.getPhoto();
            File file = new File(request.getServletContext().getRealPath("/star/img"), photo);
            boolean b = file.delete();
            if(b==false){
                throw new RuntimeException("明星图片删除失败");
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Star> findAll() {
        return starDao.selectAll();
    }
}
