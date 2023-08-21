package com.lengmu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lengmu.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
public interface ArticleDao extends BaseMapper<Article> {
    int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);
    List<Map> queryAll();
}