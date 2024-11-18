package com.Jason.domain.activity.model.entity;

import lombok.Data;

/**
 * @Author: Jason
 * @Date: 2024/11/15 11:52
 * @Description: 参与抽奖活动实体对象
 */
@Data
public class PartakeRaffleActivityEntity {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 活动ID
     */
    private Long activityId;

}
