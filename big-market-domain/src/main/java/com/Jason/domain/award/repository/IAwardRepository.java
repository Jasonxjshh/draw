package com.Jason.domain.award.repository;

import com.Jason.domain.award.model.aggregate.UserAwardRecordAggregate;

/**
 * @Author: Jason
 * @Date: 2024/11/18 15:18
 * @Description:
 */
public interface  IAwardRepository {
    void saveUserAwardRecord(UserAwardRecordAggregate userAwardRecordAggregate);
}
