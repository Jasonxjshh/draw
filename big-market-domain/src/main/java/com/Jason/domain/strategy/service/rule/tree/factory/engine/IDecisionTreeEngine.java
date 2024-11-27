package com.Jason.domain.strategy.service.rule.tree.factory.engine;

import com.Jason.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

import java.util.Date;

/**
 * @Author: Jason
 * @Date: 2024/10/5 16:45
 * @Description:
 */


public interface IDecisionTreeEngine {
    DefaultTreeFactory.StrategyAwardVO process(String userId, Long strategyId, Integer awardId, Date endDateTime);
}
