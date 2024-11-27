package com.Jason.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Jason
 * @Date: 2024/10/10 16:05
 * @Description: 抽奖奖品列表，请求对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleAwardListRequestDTO {


    // 用户ID
    private String userId;
    // 抽奖活动ID
    private Long activityId;


}
