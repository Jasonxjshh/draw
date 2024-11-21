package com.Jason.trigger.api.dto;

import lombok.Data;

/**
 * @Author: Jason
 * @Date: 2024/11/19 23:19
 * @Description:
 */

@Data
public class ActivityDrawRequestDTO {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 活动ID
     */
    private Long activityId;

}
