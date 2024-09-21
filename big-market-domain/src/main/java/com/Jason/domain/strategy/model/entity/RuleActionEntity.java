package com.Jason.domain.strategy.model.entity;

import com.Jason.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import lombok.*;

/**
 * @Author: Jason
 * @Date: 2024-09-19  15:00
 * @Description: 规则动作实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleActionEntity<T extends RuleActionEntity.RaffleEntity>{

    private String code = RuleLogicCheckTypeVO.ALLOW.getCode();

    private String info = RuleLogicCheckTypeVO.ALLOW.getInfo();

    private T data;

    private String ruleModel;


    static public class RaffleEntity {
    }

    /*
    * 抽奖前
    * */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static public class RaffleBeforeEntity extends RaffleEntity {
        private Long strategyId;

        private String ruleWeightValueKey;

        private Integer awardId;
    }

    /*
     * 抽奖中
     * */
    static public class RaffleCenterEntity extends RaffleEntity {

    }

    /*
     * 抽奖后
     * */
    static public class RaffleAfterEntity extends RaffleEntity {

    }
}
