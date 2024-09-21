package com.Jason.domain.strategy.service.rule.impl;

import com.Jason.domain.strategy.model.entity.RuleActionEntity;
import com.Jason.domain.strategy.model.entity.RuleMatterEntity;
import com.Jason.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.Jason.domain.strategy.repository.IStrategyRepository;
import com.Jason.domain.strategy.service.annotation.LogicStrategy;
import com.Jason.domain.strategy.service.rule.ILogicFilter;
import com.Jason.domain.strategy.service.rule.factory.DefaultLogicFactory;
import com.Jason.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: Jason
 * @Date: 2024-09-20  13:30
 * @Description: [抽奖前规则] 根据抽奖权重返回可抽奖范围KEY
 */
@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.RULE_WIGHT)
public class RuleWeightLogicFilter implements ILogicFilter<RuleActionEntity.RaffleBeforeEntity> {


    @Resource
    private IStrategyRepository repository;

    //模拟用户积分
    public Long userScore = 4500L;


    @Override
    public RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> filter(RuleMatterEntity ruleMatterEntity) {
        log.info("规则过滤-权重范围 userId:{} strategyId:{} ruleModel:{}", ruleMatterEntity.getUserId(), ruleMatterEntity.getStrategyId(), ruleMatterEntity.getRuleModel());

        String userId = ruleMatterEntity.getUserId();
        Long strategyId = ruleMatterEntity.getStrategyId();
        String ruleValues = repository.queryStrategyRuleValue(strategyId, ruleMatterEntity.getAwardId(), ruleMatterEntity.getRuleModel());

        Map<Long, String> analyticalValueGroup = getAnalyticalValue(ruleValues);
        if (analyticalValueGroup == null && analyticalValueGroup.isEmpty()) {
            return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                    .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                    .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                    .build();
        }
        List<Long> analyticalSortedKeys = new ArrayList<>(analyticalValueGroup.keySet());
        Collections.sort(analyticalSortedKeys);

        //找出最小的符合积分的值 也就是【4500 积分，能找到 4000:102,103,104,105】、【5000 积分，能找到 5000:102,103,104,105,106,107】
        Long nextValue = analyticalSortedKeys.stream()
                .filter(key -> key <= userScore)
                .findFirst()
                .orElse(null);

        if (nextValue != null) {
            return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                    .data(RuleActionEntity.RaffleBeforeEntity.builder()
                            .strategyId(strategyId)
                            .ruleWeightValueKey(analyticalValueGroup.get(nextValue))
                            .build())
                    .code(RuleLogicCheckTypeVO.TAKE_OVER.getCode())
                    .info(RuleLogicCheckTypeVO.TAKE_OVER.getInfo())
                    .build();
        }
        return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                .build();
    }

    private Map<Long, String> getAnalyticalValue(String ruleValues) {
        String[] ruleValueGroups = ruleValues.split(Constants.SPACE);
        Map<Long, String> ruleValueMap = new HashMap<>();
        for (String ruleValueGroup : ruleValueGroups) {
            if (ruleValueGroup == null || ruleValueGroup.isEmpty()){
                return ruleValueMap;
            }
            // 分割字符串以获取键和值
            String[] parts = ruleValueGroup.split(Constants.COLON);
            if (parts.length != 2) {
                throw new IllegalArgumentException("rule_weight rule_rule invalid input format" + ruleValueGroup);
            }
            ruleValueMap.put(Long.parseLong(parts[0]), ruleValueGroup);
        }
        return ruleValueMap;

    }
}
