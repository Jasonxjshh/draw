package com.Jason.domain.rebate.repository;

import com.Jason.domain.rebate.model.aggregate.BehaviorRebateAggregate;
import com.Jason.domain.rebate.model.valobj.BehaviorTypeVO;
import com.Jason.domain.rebate.model.valobj.DailyBehaviorRebateVO;

import java.util.List;

/**
 * @Author: Jason
 * @Date: 2024/11/27 15:48
 * @Description: 行为返利服务仓储接口
 */
public interface IBehaviorRebateRepository {

    List<DailyBehaviorRebateVO> queryDailyBehaviorRebateConfig(BehaviorTypeVO behaviorTypeVO);

    void saveUserRebateRecord(String userId, List<BehaviorRebateAggregate> behaviorRebateAggregates);

}
