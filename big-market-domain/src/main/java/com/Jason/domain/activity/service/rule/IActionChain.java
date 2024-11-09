package com.Jason.domain.activity.service.rule;

import com.Jason.domain.activity.model.entity.ActivityCountEntity;
import com.Jason.domain.activity.model.entity.ActivityEntity;
import com.Jason.domain.activity.model.entity.ActivitySkuEntity;

/**
 * @Author: Jason
 * @Date: 2024/11/8 22:27
 * @Description:
 */
public interface IActionChain extends IActionChainArmory {
    boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity);
}
