package com.Jason.domain.award.service;

import com.Jason.domain.award.model.entity.DistributeAwardEntity;
import com.Jason.domain.award.model.entity.UserAwardRecordEntity;

/**
 * @Author: Jason
 * @Date: 2024/11/18 15:13
 * @Description:
 */
public interface IAwardService {
    void saveUserAwardRecord(UserAwardRecordEntity userAwardRecordEntity);

    /**
     * 配送发货奖品
     */
    void distributeAward(DistributeAwardEntity distributeAwardEntity);
}
