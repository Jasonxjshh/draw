package com.Jason.domain.strategy.service.rule.chain.impl;

import com.Jason.domain.strategy.service.armory.IStrategyDispatch;
import com.Jason.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.Jason.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: Jason
 * @Date: 2024-09-25  16:14
 * @Description: 兜底逻辑链
 */
@Slf4j
@Component("rule_default")
public class DefaultLogicChain extends AbstractLogicChain {

    @Resource
    private IStrategyDispatch strategyDispatch;

    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {
        Integer randomAwardId = strategyDispatch.getRandomAwardId(strategyId);
        log.info("抽奖责任链---默认处理：userId: {} strategyId: {} ruleModel: {} awardId：{}", userId, strategyId, ruleModel(), randomAwardId);
        return new DefaultChainFactory.StrategyAwardVO(randomAwardId, ruleModel());
    }

    @Override
    public String ruleModel() {
        return "rule_default";
    }
}
