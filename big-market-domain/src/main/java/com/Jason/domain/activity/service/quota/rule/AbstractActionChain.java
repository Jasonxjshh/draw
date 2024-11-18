package com.Jason.domain.activity.service.quota.rule;

/**
 * @Author: Jason
 * @Date: 2024/11/8 22:31
 * @Description:
 */
public abstract class AbstractActionChain implements IActionChain{
    private IActionChain next;

    @Override
    public IActionChain appendNext(IActionChain next) {
        this.next = next;
        return next;
    }

    @Override
    public IActionChain next() {
        return next;
    }
}
