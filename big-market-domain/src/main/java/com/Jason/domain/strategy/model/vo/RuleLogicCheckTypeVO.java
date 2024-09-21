package com.Jason.domain.strategy.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Jason
 * @Date: 2024-09-19  15:20
 * @Description: 规则逻辑检查类型
 */
@Getter
@AllArgsConstructor
public enum RuleLogicCheckTypeVO {

    ALLOW("0000", "放行; 执行后续的流程, 不受规则引擎影响"),
    TAKE_OVER("0001", "接管; 后续的流程, 受规则引擎执行结果的影响")
    ;

    private final String code;
    private final String info;
}
