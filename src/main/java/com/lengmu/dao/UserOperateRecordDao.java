package com.lengmu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lengmu.entity.UserOperateRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserOperateRecordDao extends BaseMapper<UserOperateRecord> {
    int deleteByPrimaryKey(Integer id);

    int insert(UserOperateRecord record);

    int insertSelective(UserOperateRecord record);

    UserOperateRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserOperateRecord record);

    int updateByPrimaryKey(UserOperateRecord record);
}