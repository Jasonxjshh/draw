package com.Jason.domain.strategy.service.armory;

import java.util.Date;

/**
 * @Author: Jason
 * @Date: 2024-09-18  14:20
 * @Description: 策略抽奖的调度接口
 */
public interface IStrategyDispatch {

    Integer getRandomAwardId(Long strategyId);

    Integer getRandomAwardId(Long strategyId,String ruleWeightValue);

    /**
     * 根据策略ID和奖品ID，扣减奖品缓存库存
     *
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return 扣减结果
     */
    Boolean subtractionAwardStock(Long strategyId, Integer awardId, Date endDateTime);
}