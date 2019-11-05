package com.baizhi.service.impl;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("bannerService")
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Banner> findAll(Integer page,Integer rows) {
        Banner banner = new Banner();
        int start=(page-1)*rows;
        RowBounds rowBounds = new RowBounds(start,rows);
        List<Banner> banners = bannerDao.selectByRowBounds(banner,rowBounds);
        return banners;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public int findTotalCounts() {
        Banner banner=new Banner();
        return bannerDao.selectCount(banner);
    }

    @Override
    public String add(Banner banner) {
        banner.setId(UUID.randomUUID().toString());
        banner.setCreateDate(new Date());
        String substring = banner.getCover().substring(banner.getCover().lastIndexOf("\\") + 1);
        banner.setCover(substring);
        int i = bannerDao.insertSelective(banner);
        if(i==0){
            throw new RuntimeException("添加失败");
        }
        return banner.getId();//通过id把图片添加进去
    }

    @Override
    public void update(Banner banner) {
        if("".equals(banner.getCover())){
            banner.setCover(null);
        }
        try {
            bannerDao.updateByPrimaryKeySelective(banner);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }
    }

    @Override
    public void delete(String id, HttpServletRequest request) {
        Banner banner = bannerDao.selectByPrimaryKey(id);
        int i = bannerDao.deleteByPrimaryKey(id);
        if(i==0){
            throw new RuntimeException("删除失败");
        }else{
            String cover = banner.getCover();
            File file = new File(request.getServletContext().getRealPath("/banner/img/"), cover);
            boolean b = file.delete();
            if(b==false){
                throw new RuntimeException("图片删除失败");
            }
        }
    }
}
