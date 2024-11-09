package com.Jason.domain.activity.service.rule;

import com.Jason.domain.activity.model.entity.ActivityCountEntity;
import com.Jason.domain.activity.model.entity.ActivityEntity;
import com.Jason.domain.activity.model.entity.ActivitySkuEntity;

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
