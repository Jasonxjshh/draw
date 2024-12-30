package com.Jason.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Jason
 * @Date: 2024/12/25 22:12
 * @Description:
 */

@Getter
@AllArgsConstructor
public enum UserAccountStatusVO {
    open("open", "开启"),
    close("close", "关闭"),
    ;

    private final String code;
    private final String desc;

}
