package com.Jason.domain.strategy.service.rule.tree.impl;

import com.Jason.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.Jason.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.Jason.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: Jason
 * @Date: 2024/10/5 16:41
 * @Description: 次数锁节点
 */
@Component("rule_lock")
public class RuleLockLogicTreeNode implements ILogicTreeNode {
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId) {
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
                .build();
    }
}
