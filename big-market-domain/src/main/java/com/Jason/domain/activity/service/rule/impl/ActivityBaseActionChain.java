package com.Jason.domain.activity.service.rule.impl;

import com.Jason.domain.activity.model.entity.ActivityCountEntity;
import com.Jason.domain.activity.model.entity.ActivityEntity;
import com.Jason.domain.activity.model.entity.ActivitySkuEntity;
import com.Jason.domain.activity.service.rule.AbstractActionChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @Author: Jason
 * @Date: 2024/11/8 22:33
 * @Description: 活动规则过滤【日期、状态】
 */
@Slf4j
@Component("activity_base_action")
public class ActivityBaseActionChain extends AbstractActionChain {
    @Override
    public boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {
        log.info("活动责任链-基础信息【有效期、状态】校验开始。");
        return next().action(activitySkuEntity, activityEntity, activityCountEntity);

    }
}