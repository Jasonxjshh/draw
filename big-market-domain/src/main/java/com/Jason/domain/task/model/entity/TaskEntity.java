package com.Jason.domain.task.model.entity;

import lombok.Data;

/**
 * @Author: Jason
 * @Date: 2024/11/18 16:07
 * @Description:
 */
@Data
public class TaskEntity {
    /** 活动ID */
    private String userId;
    /** 消息主题 */
    private String topic;
    /** 消息编号 */
    private String messageId;
    /** 消息主体 */
    private String message;

}
