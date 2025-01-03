package com.Jason.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Jason
 * @Date: 2024/10/10 16:05
 * @Description: 抽奖请求参数
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleStrategyRequestDTO {
    // 抽奖策略ID
    private Long strategyId;

}
