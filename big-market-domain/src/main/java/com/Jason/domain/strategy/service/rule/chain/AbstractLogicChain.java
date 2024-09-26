package com.Jason.domain.strategy.service.rule.chain;

/**
 * @Author: Jason
 * @Date: 2024-09-25  16:00
 * @Description: 抽奖策略责任链抽象类
 */
public abstract class AbstractLogicChain implements ILogicChain{
    private ILogicChain next;

    public ILogicChain appendNext(ILogicChain next) {
        this.next = next;
        return next;
    }

    public ILogicChain next() {
        return next;
    }

    public abstract String ruleModel();
}
