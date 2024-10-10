package com.Jason.domain.strategy.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Jason
 * @Date: 2024/10/8 11:58
 * @Description: 策略奖品库存Key标识值对象
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class StrategyAwardStockKeyVO {

    // 策略ID
    private Long strategyId;
    // 奖品ID
    private Integer awardId;

}
