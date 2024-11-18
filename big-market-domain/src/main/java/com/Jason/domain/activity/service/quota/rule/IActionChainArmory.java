package com.Jason.domain.activity.service.quota.rule;

/**
 * @Author: Jason
 * @Date: 2024/11/8 22:29
 * @Description:
 */
public interface IActionChainArmory {
    IActionChain appendNext(IActionChain next);

    IActionChain next();
}
