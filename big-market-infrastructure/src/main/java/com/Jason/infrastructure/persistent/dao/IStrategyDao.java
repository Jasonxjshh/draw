package com.Jason.infrastructure.persistent.dao;

import com.Jason.infrastructure.persistent.po.Strategy;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Jason
 * @Date: 2024-09-11  16:53
 * @Description:  策略表 Dao
 */
@Mapper
public interface IStrategyDao {
    Strategy queryStrategyByStrategyId(Long strategyId);
}
