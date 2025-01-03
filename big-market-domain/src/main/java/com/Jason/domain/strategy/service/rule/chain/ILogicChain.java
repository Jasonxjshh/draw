package com.Jason.domain.strategy.service.rule.chain;

import com.Jason.domain.strategy.service.rule.chain.factory.DefaultChainFactory;

/**
 * @Author: Jason
 * @Date: 2024-09-25  15:54
 * @Description: 抽奖规则责任链
 */
public interface ILogicChain extends ILogicChainArmory{


    /**
     * 责任链接口
     *
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @return 奖品ID
     */
    DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId);
}
