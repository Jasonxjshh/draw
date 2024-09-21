package com.Jason.infrastructure.persistent.dao;

import com.Jason.infrastructure.persistent.po.StrategyRule;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Jason
 * @Date: 2024-09-11  16:53
 * @Description:  策略规则表 Dao
 */
@Mapper
public interface IStrategyRulesDao {
    StrategyRule queryStrategyRule(Long strategyId, String ruleModel);

    String queryStrategyRuleValue(StrategyRule strategyRule);
}
