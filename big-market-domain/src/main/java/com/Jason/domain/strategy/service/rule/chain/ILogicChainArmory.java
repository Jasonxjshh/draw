package com.Jason.domain.strategy.service.rule.chain;

/**
 * @Author: Jason
 * @Date: 2024-09-25  16:33
 * @Description:
 */
public interface ILogicChainArmory {

    ILogicChain appendNext(ILogicChain next);

    ILogicChain next();
}
