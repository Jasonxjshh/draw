package com.Jason.domain.strategy.service.rule.chain.impl;

import com.Jason.domain.strategy.repository.IStrategyRepository;
import com.Jason.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.Jason.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: Jason
 * @Date: 2024-09-25  16:01
 * @Description: 黑名单抽奖
 */

@Slf4j
@Component("rule_blacklist")
public class BlackListLogicChain extends AbstractLogicChain {

    @Resource
    private IStrategyRepository repository;

    @Override
    public Integer logic(String userId, Long strategyId) {
        log.info("抽奖责任链---黑名单处理开始：userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());
        String ruleValues = repository.queryStrategyRuleValue(strategyId, ruleModel());
        String[] split = ruleValues.split(Constants.COLON);
        Integer awardId = Integer.parseInt(split[0]);
        String[] blackUserIDs = split[1].split(Constants.SPLIT);
        for (String blackUserID : blackUserIDs) {
            if (userId.equals(blackUserID)) {
                log.info("抽奖责任链---黑名单接管：userId: {} strategyId: {} ruleModel: {} awardId: {}", userId, strategyId, ruleModel(), awardId);
                return awardId;
            }
        }
        log.info("抽奖责任链---黑名单放行：userId: {} strategyId: {} ruleModel: {} awardId: {}", userId, strategyId, ruleModel(), awardId);
        return next().logic(userId, strategyId);
    }

    @Override
    public String ruleModel() {
        return "rule_blacklist";
    }
}
