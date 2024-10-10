package com.Jason.infrastructure.persistent.dao;

import com.Jason.domain.strategy.model.entity.StrategyAwardEntity;
import com.Jason.infrastructure.persistent.po.StrategyAward;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: Jason
 * @Date: 2024-09-11  16:53
 * @Description: 抽奖策略奖品配置表 Dao
 */
@Mapper
public interface IStrategyAwardDao {
    List<StrategyAward> queryStrategyAwardByStrategyId(Long strategyId);

    String queryStrategyAwardRuleModel(StrategyAward strategyAward);

    void updateStrategyAwardStock(StrategyAward strategyAward);
}
