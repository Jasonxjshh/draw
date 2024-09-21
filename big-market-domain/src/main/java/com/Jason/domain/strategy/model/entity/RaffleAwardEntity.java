package com.Jason.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Jason
 * @Date: 2024-09-19  14:35
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleAwardEntity {

    private Long strategyId;

    private Integer awardId;

    private String awardKey;

    private String awardConfig;

    private String awardDesc;
}
