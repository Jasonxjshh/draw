package com.Jason.domain.award.model.aggregate;

import com.Jason.domain.award.model.entity.TaskEntity;
import com.Jason.domain.award.model.entity.UserAwardRecordEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Jason
 * @Date: 2024/11/18 15:44
 * @Description: 用户中奖记录聚合对象 【聚合代表一个事务操作】
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAwardRecordAggregate {

    private UserAwardRecordEntity userAwardRecordEntity;

    private TaskEntity taskEntity;

}
