package com.Jason.domain.activity.service.quota;

import com.Jason.domain.activity.model.entity.ActivityCountEntity;
import com.Jason.domain.activity.model.entity.ActivityEntity;
import com.Jason.domain.activity.model.entity.ActivitySkuEntity;
import com.Jason.domain.activity.repository.IActivityRepository;
import com.Jason.domain.activity.service.quota.rule.factory.DefaultActivityChainFactory;

/**
 * @Author: Jason
 * @Date: 2024/11/8 22:18
 * @Description: 抽奖活动支持
 */
public class RaffleActivityAccountQuotaSupport {
    protected DefaultActivityChainFactory defaultActivityChainFactory;

    protected IActivityRepository activityRepository;

    public RaffleActivityAccountQuotaSupport(IActivityRepository activityRepository, DefaultActivityChainFactory defaultActivityChainFactory) {
        this.activityRepository = activityRepository;
        this.defaultActivityChainFactory = defaultActivityChainFactory;
    }

    public ActivitySkuEntity queryActivitySku(Long sku) {
        return activityRepository.queryActivitySku(sku);
    }

    public ActivityEntity queryRaffleActivityByActivityId(Long activityId) {
        return activityRepository.queryRaffleActivityByActivityId(activityId);
    }

    public ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId) {
        return activityRepository.queryRaffleActivityCountByActivityCountId(activityCountId);
    }

}
