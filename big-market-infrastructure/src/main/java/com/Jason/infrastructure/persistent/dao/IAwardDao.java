package com.Jason.infrastructure.persistent.dao;

import com.Jason.infrastructure.persistent.po.Award;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: Jason
 * @Date: 2024-09-11  16:52
 * @Description: 奖品表 Dao
 */
@Mapper
public interface IAwardDao {
    List<Award> queryAwardList();
}
