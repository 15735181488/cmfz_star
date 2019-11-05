package com.baizhi.service.impl;

import com.baizhi.dao.ArticleDao;
import com.baizhi.dao.StarDao;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("articleService")
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private StarDao starDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        Map<String,Object> map=new HashMap<>();
        Article article=new Article();
        RowBounds rowBounds=new RowBounds((page-1)*rows,rows);
        List<Article> articles = articleDao.selectByRowBounds(article, rowBounds);
        int totalCounts = articleDao.selectCount(article);
        map.put("rows",articles);
        map.put("page",page);
        int totalPage=totalCounts%rows==0?totalCounts/rows:totalCounts/rows+1;
        map.put("total",totalPage);
        map.put("records",totalCounts);
        return map;
    }

    @Override
    public void delete(String id) {
        int i = articleDao.deleteByPrimaryKey(id);
        if(i==0){
            throw new RuntimeException("文章删除失败");
        }
    }

    @Override
    public void add(Article article) {
        article.setId(UUID.randomUUID().toString());
        article.setCreateDate(new Date());
        int i = articleDao.insert(article);
        if(i==0){
            throw new RuntimeException("文章添加失败");
        }
    }

    @Override
    public void update(Article article) {
        int i = articleDao.updateByPrimaryKeySelective(article);
        if(i==0){
            throw new RuntimeException("文章修改失败");
        }
    }
}
