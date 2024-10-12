package com.Jason.domain.strategy.service;

import com.Jason.domain.strategy.model.entity.StrategyAwardEntity;

import java.util.List;

/**
 * @Author: Jason
 * @Date: 2024/10/10 16:18
 * @Description:
 */
public interface IRaffleAward {
    List<StrategyAwardEntity> queryRaffleStrategyAwardList(Long strategyId);
}
