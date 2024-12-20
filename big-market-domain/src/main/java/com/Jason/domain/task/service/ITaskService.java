package com.Jason.domain.task.service;



import com.Jason.domain.task.model.entity.TaskEntity;

import java.util.List;

/**
 * @Author: Jason
 * @Date: 2024/11/18 15:13
 * @Description:
 */
public interface ITaskService {
    /**
     * 查询发送MQ失败和超时1分钟未发送的MQ
     *
     * @return 未发送的任务消息列表10条
     */
    List<TaskEntity> queryNoSendMessageTaskList();

    void sendMessage(TaskEntity taskEntity);

    void updateTaskSendMessageCompleted(String userId, String messageId);

    void updateTaskSendMessageFail(String userId, String messageId);

}
