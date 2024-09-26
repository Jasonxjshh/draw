package com.Jason.domain.strategy.service.rule.chain.impl;

import com.Jason.domain.strategy.model.entity.RuleActionEntity;
import com.Jason.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.Jason.domain.strategy.repository.IStrategyRepository;
import com.Jason.domain.strategy.service.armory.IStrategyDispatch;
import com.Jason.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.Jason.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: Jason
 * @Date: 2024-09-25  16:12
 * @Description:
 */
@Slf4j
@Component("rule_weight")
public class RuleWeightLogicChain extends AbstractLogicChain {


    @Resource
    private IStrategyRepository repository;

    @Resource
    private IStrategyDispatch strategyDispatch;

    //模拟用户积分
    public Long userScore = 4500L;

    @Override
    public Integer logic(String userId, Long strategyId) {
        log.info("抽奖责任链---抽奖权重处理开始：userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());
        String ruleValues = repository.queryStrategyRuleValue(strategyId, ruleModel());

        Map<Long, String> analyticalValueGroup = getAnalyticalValue(ruleValues);
        if (analyticalValueGroup == null && analyticalValueGroup.isEmpty()) {
            return next().logic(userId, strategyId);
        }
        List<Long> analyticalSortedKeys = new ArrayList<>(analyticalValueGroup.keySet());
        Collections.sort(analyticalSortedKeys);

        //找出最小的符合积分的值 也就是【4500 积分，能找到 4000:102,103,104,105】、【5000 积分，能找到 5000:102,103,104,105,106,107】
        Long nextValue = analyticalSortedKeys.stream()
                .sorted(Comparator.reverseOrder())
                .filter(key -> key <= userScore)
                .findFirst()
                .orElse(null);

        if (nextValue != null) {
            log.info("抽奖责任链---抽奖权重接管：userId: {} strategyId: {} ruleModel: {} ruleValue: {}", userId, strategyId, ruleModel(), analyticalValueGroup.get(nextValue));
            return strategyDispatch.getRandomAwardId(strategyId, analyticalValueGroup.get(nextValue));
        }
        log.info("抽奖责任链---抽奖权重放行：userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());
        return next().logic(userId, strategyId);
    }

    @Override
    public String ruleModel() {
        return "rule_weight";
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
