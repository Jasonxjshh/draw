package com.Jason.domain.task.repository;



import com.Jason.domain.task.model.entity.TaskEntity;

import java.util.List;

/**
 * @Author: Jason
 * @Date: 2024/11/18 16:02
 * @Description:
 */
public interface ITaskRepository {
    List<TaskEntity> queryNoSendMessageTaskList();

    void sendMessage(TaskEntity taskEntity);

    void updateTaskSendMessageCompleted(String userId, String messageId);

    void updateTaskSendMessageFail(String userId, String messageId);
}
