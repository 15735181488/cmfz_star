package com.baizhi.service.impl;

import com.baizhi.annotation.RedisCache;
import com.baizhi.dao.ArticleDao;
import com.baizhi.dao.StarDao;
import com.baizhi.entity.Article;
import com.baizhi.repository.ArticleRepository;
import com.baizhi.service.ArticleService;
import org.apache.commons.collections4.IterableUtils;
import org.apache.ibatis.session.RowBounds;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
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
    @RedisCache
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
        } else {
            articleRepository.deleteById(id);
        }
    }

    @Override
    public void add(Article article) {
        article.setId(UUID.randomUUID().toString());
        article.setCreateDate(new Date());
        int i = articleDao.insert(article);
        articleRepository.save(article);
        if(i==0){
            throw new RuntimeException("文章添加失败");
        }
    }

    @Override
    public void update(Article article) {
        int i = articleDao.updateByPrimaryKeySelective(article);
        if(i==0){
            throw new RuntimeException("文章修改失败");
        } else {
            Article article1 = articleDao.selectByPrimaryKey(article.getId());
            articleRepository.save(article1);
        }
    }

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public List<Article> search(String input) {
        if ("".equals(input)) {
            Iterable<Article> all = articleRepository.findAll();
            List<Article> articles = IterableUtils.toList(all);
            return articles;
        } else {
            HighlightBuilder highlightBuilder = new HighlightBuilder()
                    .field("*")
                    //.preTags("<span color='red'>")
                    //.postTags("</span>")
                    .requireFieldMatch(false);
            NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.queryStringQuery(input).field("title").field("author").field("brief").field("content"))
                    .withSort(SortBuilders.scoreSort())
                    //.withHighlightBuilder(highlightBuilder)
                    .build();
            AggregatedPage<Article> articles = elasticsearchTemplate.queryForPage(nativeSearchQuery, Article.class, new SearchResultMapper() {
                @Override
                public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
                    SearchHits searchHits = response.getHits();
                    SearchHit[] hits = searchHits.getHits();
                    List<Article> list = new ArrayList<>();
                    for (SearchHit hit : hits) {
                        Map<String, Object> map = hit.getSourceAsMap();
                        Article article = new Article();
                        article.setId(map.get("id").toString());
                        article.setTitle(map.get("title").toString());
                        article.setAuthor(map.get("author").toString());
                        article.setBrief(map.get("brief").toString());
                        article.setContent(map.get("content").toString());
                        String date = map.get("createDate").toString();
                        article.setCreateDate(new Date(Long.valueOf(date)));
                        Map<String, HighlightField> fieldMap = hit.getHighlightFields();
                        if (fieldMap.get("title") != null) {
                            article.setTitle(fieldMap.get("title").getFragments()[0].toString());
                        }
                        if (fieldMap.get("author") != null) {
                            article.setAuthor(fieldMap.get("author").getFragments()[0].toString());
                        }
                        if (fieldMap.get("brief") != null) {
                            article.setBrief(fieldMap.get("brief").getFragments()[0].toString());
                        }
                        if (fieldMap.get("content") != null) {
                            article.setContent(fieldMap.get("content").getFragments()[0].toString());
                        }
                        list.add(article);
                    }
                    return new AggregatedPageImpl<T>((List<T>) list);
                }

                @Override
                public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
                    return null;
                }
            });
            return articles.getContent();
        }
    }

}
