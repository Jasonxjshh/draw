package com.Jason.infrastructure.persistent.dao;

import com.Jason.infrastructure.persistent.po.DailyBehaviorRebate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: Jason
 * @Date: 2024/11/27 15:34
 * @Description: 日常行为返利活动配置
 */
@Mapper
public interface IDailyBehaviorRebateDao {
    List<DailyBehaviorRebate> queryDailyBehaviorRebateByBehaviorType(String behaviorType);
}
