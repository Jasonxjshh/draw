package com.Jason.domain.strategy.service.armory;

/**
 * @Author: Jason
 * @Date: 2024-09-18  14:20
 * @Description: 策略抽奖的调度接口
 */
public interface IStrategyDispatch {

    Integer getRandomAwardId(Long strategyId);

    Integer getRandomAwardId(Long strategyId,String ruleWeightValue);
}