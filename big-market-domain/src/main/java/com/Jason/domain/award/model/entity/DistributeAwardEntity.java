package com.Jason.domain.award.model.entity;

import lombok.Data;

/**
 * @Author: Jason
 * @Date: 2024/12/25 16:07
 * @Description:
 */
@Data
public class DistributeAwardEntity {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 奖品ID
     */
    private Integer awardId;
    /**
     * 奖品配置信息
     */
    private String awardConfig;
}
