package com.Jason.domain.rebate.service;

import com.Jason.domain.rebate.model.entity.BehaviorEntity;

import java.util.List;

/**
 * @Author: Jason
 * @Date: 2024/11/27 15:30
 * @Description:
 */
public interface IBehaviorRebateService {
    /**
     * 创建行为动作的入账订单
     *
     * @param behaviorEntity 行为实体对象
     * @return 订单ID
     */

    List<String> createOrder(BehaviorEntity behaviorEntity);
}
