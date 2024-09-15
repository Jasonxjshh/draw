package com.Jason.domain.strategy.service.armory;

/**
 * @Author: Jason
 * @Date: 2024-09-13  17:48
 * @Description: 策略装配库, 负责初始化策略计算
 */
public interface IStrategyArmory {
    void assembleLotteryStrategy(Long strategyId);

    Integer getRandomAwardId(Long strategyId);
}
