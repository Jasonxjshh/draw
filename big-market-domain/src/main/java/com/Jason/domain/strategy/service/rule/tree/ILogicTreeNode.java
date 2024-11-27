package com.Jason.domain.strategy.service.rule.tree;

import com.Jason.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

import java.util.Date;

/**
 * @Author: Jason
 * @Date: 2024/10/5 16:41
 * @Description:
 */
public interface ILogicTreeNode {
    DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue, Date endDateTime);
}
