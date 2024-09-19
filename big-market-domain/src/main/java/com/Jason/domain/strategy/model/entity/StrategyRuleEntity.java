package com.Jason.domain.strategy.model.entity;

import com.Jason.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Jason
 * @Date: 2024-09-18  15:05
 * @Description: 策略规则实体
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyRuleEntity {
    /** 抽奖策略ID */
    private Long strategyId;
    /** 抽奖奖品ID【规则类型为策略，则不需要奖品ID】 */
    private Integer awardId;
    /** 抽象规则类型；1-策略规则、2-奖品规则 */
    private Integer ruleType;
    /** 抽奖规则类型【rule_random - 随机值计算、rule_lock - 抽奖几次后解锁、rule_luck_award - 幸运奖(兜底奖品)】 */
    private String ruleModel;
    /** 抽奖规则比值 */
    private String ruleValue;
    /** 抽奖规则描述 */
    private String ruleDesc;


    /**
     * 抽奖规则权重值
     * 4000:102,103,104,105 5000:102,102,103,104,105,106,107 6000:102,103,104,105 5000:102,102,103,104,105,106,107,108,109
     * @return Map<String, List<Integer>>
     */
    public Map<String, List<Integer>> getRuleWeightValues() {
        if (!"rule_weight".equals(ruleModel)) return null;
        String[] ruleValues = ruleValue.split(Constants.SPACE);
        Map<String, List<Integer>> result = new HashMap<>();
        for (String ruleValue : ruleValues) {
            if (ruleValue == null || ruleValue.isEmpty()) return result;
            String[] ruleValueArr = ruleValue.split(Constants.COLON);
            if (ruleValueArr.length != 2) {
                throw new IllegalArgumentException("rule_weight rule_rule invalid input format" + ruleValue);
            }
            String[] valueStrings = ruleValueArr[1].split(Constants.SPLIT);
            List<Integer> values = new ArrayList<>();
            for (String valueString : valueStrings) {
                values.add(Integer.valueOf(valueString));
            }
            result.put(ruleValue, values);
        }
        return result;
    }
}
