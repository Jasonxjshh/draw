package com.Jason.domain.strategy.repository;

import com.Jason.domain.strategy.model.entity.StrategyAwardEntity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: Jason
 * @Date: 2024-09-14  13:40
 * @Description: 策略仓储蹭接口
 */
public interface IStrategyRepository {

    List<StrategyAwardEntity> assembleLotteryStrategy(Long strategyId);

    void storeStrategyAwardSearchRateTables(Long strategyId, BigDecimal rateRange, HashMap<Integer, Integer> shuffleStrategyAwardSearchRateTable);

    int getRateRang(Long strategyId);

    Integer getStrategyAwardAssemble(Long strategyId, int rateKey);
}
