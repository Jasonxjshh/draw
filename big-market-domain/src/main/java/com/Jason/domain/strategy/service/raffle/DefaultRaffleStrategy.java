package com.Jason.domain.strategy.service.raffle;

import com.Jason.domain.strategy.model.entity.RaffleFactorEntity;
import com.Jason.domain.strategy.model.entity.RuleActionEntity;
import com.Jason.domain.strategy.model.entity.RuleMatterEntity;
import com.Jason.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.Jason.domain.strategy.model.vo.RuleTreeVO;
import com.Jason.domain.strategy.model.vo.StrategyAwardRuleModelVO;
import com.Jason.domain.strategy.service.AbstractRaffleStrategy;
import com.Jason.domain.strategy.service.rule.chain.ILogicChain;
import com.Jason.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.Jason.domain.strategy.service.rule.filter.ILogicFilter;
import com.Jason.domain.strategy.service.rule.filter.factory.DefaultLogicFactory;
import com.Jason.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.Jason.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: Jason
 * @Date: 2024-09-21  13:40
 * @Description: 默认抽奖策略
 */
@Slf4j
@Service
public class DefaultRaffleStrategy extends AbstractRaffleStrategy {



    @Override
    public DefaultChainFactory.StrategyAwardVO raffleLogicChain(String userId, Long strategyId) {
        ILogicChain logicChain = chainFactory.openLogicChain(strategyId);
        return logicChain.logic(userId, strategyId);
    }

    @Override
    public DefaultTreeFactory.StrategyAwardVO raffleLogicTree(String userId, Long strategyId, Integer awardId) {
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = repository.queryStrategyAwardRuleModel(strategyId, awardId);
        if (null == strategyAwardRuleModelVO) {
            return DefaultTreeFactory.StrategyAwardVO.builder().awardId(awardId).build();
        }
        RuleTreeVO ruleTreeVO = repository.queryRuleTreeVOByTreeId(strategyAwardRuleModelVO.getRuleModel());
        if (null == ruleTreeVO) {
            throw new RuntimeException("存在抽奖策略配置的规则模型 Key，未在库表 rule_tree、rule_tree_node、rule_tree_line 配置对应的规则树信息 " + strategyAwardRuleModelVO.getRuleModel());
        }
        IDecisionTreeEngine treeEngine = treeFactory.openLogicTree(ruleTreeVO);
        return treeEngine.process(userId, strategyId, awardId);
    }
}
