package com.Jason.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Jason
 * @Date: 2024/11/15 11:52
 * @Description: 参与抽奖活动实体对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
