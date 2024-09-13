package com.Jason.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Jason
 * @Date: 2024/09/11/16:11
 * @Description: 策略表
 */
@Data
public class Strategy {

    private Long id;

    private Long strategyId;

    private String strategyDesc;

    private Date createTime;

    private Date updateTime;

}
