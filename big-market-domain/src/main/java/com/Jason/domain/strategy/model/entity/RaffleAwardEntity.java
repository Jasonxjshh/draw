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


    /** 奖品ID */
    private Integer awardId;
    /** 奖品配置信息 */
    private String awardConfig;
    /** 奖品顺序号 */
    private Integer sort;

}
