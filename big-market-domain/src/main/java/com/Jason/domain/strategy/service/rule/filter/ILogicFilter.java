package com.Jason.domain.strategy.service.rule.filter;

import com.Jason.domain.strategy.model.entity.RuleActionEntity;
import com.Jason.domain.strategy.model.entity.RuleMatterEntity;

/**
 * @Author: Jason
 * @Date: 2024-09-19  15:02
 * @Description: 抽奖规则过滤接口
 */
public interface ILogicFilter<T extends RuleActionEntity.RaffleEntity> {

    RuleActionEntity<T> filter(RuleMatterEntity ruleMatterEntity);
}
