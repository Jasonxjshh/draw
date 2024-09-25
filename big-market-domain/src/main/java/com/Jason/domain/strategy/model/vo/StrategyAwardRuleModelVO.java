package com.Jason.domain.strategy.model.vo;

import com.Jason.domain.strategy.service.rule.filter.factory.DefaultLogicFactory;
import com.Jason.types.common.Constants;
import lombok.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Jason
 * @Date: 2024-09-21  18:06
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StrategyAwardRuleModelVO {
    private String ruleModel;

    public String[] raffleCenterRuleModels() {

        List<String> collect = Arrays.stream(ruleModel.split(Constants.SPLIT))
                .filter(DefaultLogicFactory.LogicModel::isCenter)
                .collect(Collectors.toList());

        return collect.toArray(new String[0]);
    }

    public String[] raffleAfterRuleModels() {
        List<String> collect = Arrays.stream(ruleModel.split(Constants.SPLIT))
                .filter(DefaultLogicFactory.LogicModel::isAfter)
                .collect(Collectors.toList());

        return collect.toArray(new String[0]);
    }
}
