package com.Jason.domain.strategy.service;

import com.Jason.domain.strategy.model.entity.RaffleAwardEntity;
import com.Jason.domain.strategy.model.entity.RaffleFactorEntity;

/**
 * @Author: Jason
 * @Date: 2024-09-19  14:27
 * @Description: 抽奖策略接口
 */
public interface IRaffleStrategy {

    RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactor);

}
