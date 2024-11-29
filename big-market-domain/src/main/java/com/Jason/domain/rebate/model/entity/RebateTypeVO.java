package com.Jason.domain.rebate.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Jason
 * @Date: 2024/11/28 11:50
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum RebateTypeVO {

    SKU("sku", "活动库存充值商品"),
    INTEGRAL("integral", "用户活动积分"),
    ;

    private final String code;
    private final String info;

}
