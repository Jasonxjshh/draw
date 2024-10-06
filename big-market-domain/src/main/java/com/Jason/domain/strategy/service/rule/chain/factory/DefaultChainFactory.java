package com.Jason.domain.strategy.service.rule.chain.factory;

import com.Jason.domain.strategy.model.entity.StrategyEntity;
import com.Jason.domain.strategy.repository.IStrategyRepository;
import com.Jason.domain.strategy.service.rule.chain.ILogicChain;
import com.Jason.domain.strategy.service.rule.chain.impl.DefaultLogicChain;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: Jason
 * @Date: 2024-09-25  17:13
 * @Description: 默认的抽奖规则链工厂
 */
@Slf4j
@Service
public class DefaultChainFactory {
    private final Map<String, ILogicChain> logicChainMap;

    private final IStrategyRepository repository;


    public DefaultChainFactory(Map<String, ILogicChain> logicChainMap, IStrategyRepository repository) {
        this.logicChainMap = logicChainMap;
        this.repository = repository;
    }


    public ILogicChain openLogicChain(Long strategyId) {
        StrategyEntity strategy = repository.queryStrategyEntityByStrategyId(strategyId);
        String[] ruleModels = strategy.ruleModels();
        if (ruleModels == null || ruleModels.length == 0){
            return logicChainMap.get("default");
        }
        ILogicChain logicChain = logicChainMap.get(ruleModels[0]);
        ILogicChain current = logicChain;
        for (int i = 1; i < ruleModels.length; i++) {
            current = current.appendNext(logicChainMap.get(ruleModels[i]));
        }
        current.appendNext(logicChainMap.get("default"));
        return logicChain;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO {
        /** 抽奖奖品ID - 内部流转使用 */
        private Integer awardId;
        /**  */
        private String logicModel;
    }

    @Getter
    @AllArgsConstructor
    public enum LogicModel {

        RULE_DEFAULT("rule_default", "默认抽奖"),
        RULE_BLACKLIST("rule_blacklist", "黑名单抽奖"),
        RULE_WEIGHT("rule_weight", "权重规则"),
        ;

        private final String code;
        private final String info;

    }

}
