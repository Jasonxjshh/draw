package com.Jason.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Jason
 * @Date: 2024/10/17 16:44
 * @Description:
 */
@Data
public class RaffleActivityCount {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 活动次数编号
     */
    private Long activityCountId;

    /**
     * 总次数
     */
    private Integer totalCount;

    /**
     * 日次数
     */
    private Integer dayCount;

    /**
     * 月次数
     */
    private Integer monthCount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
