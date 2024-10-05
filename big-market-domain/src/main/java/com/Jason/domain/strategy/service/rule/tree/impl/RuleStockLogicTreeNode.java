package com.Jason.domain.strategy.service.rule.tree.impl;

import com.Jason.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.Jason.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.Jason.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: Jason
 * @Date: 2024/10/5 17:00
 * @Description: 库存锁节点
 */
@Slf4j
@Component("rule_stock")
public class RuleStockLogicTreeNode implements ILogicTreeNode {

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId) {
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .build();
    }

}
