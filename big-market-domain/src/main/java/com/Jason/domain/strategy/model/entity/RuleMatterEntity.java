package com.Jason.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Jason
 * @Date: 2024-09-19  15:02
 * @Description: 规则物料实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleMatterEntity {

    private String userId;

    private Long strategyId;

    private Integer awardId;

    private String ruleModel;
}
