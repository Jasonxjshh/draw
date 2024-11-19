package com.Jason.domain.award.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Jason
 * @Date: 2024/11/18 15:28
 * @Description:
 */

@Getter
@AllArgsConstructor
public enum AwardStateVO {

    create("create", "创建"),
    completed("completed", "发奖完成"),
    fail("fail", "发奖失败");

    private final String code;
    private final String desc;

}
