package com.Jason.domain.activity.service;

import com.Jason.domain.activity.repository.IActivityRepository;
import org.springframework.stereotype.Service;

/**
 * @Author: Jason
 * @Date: 2024/11/5 21:43
 * @Description: 抽奖活动服务
 */
@Service
public class RaffleActivityService extends AbstractRaffleActivity{

    public RaffleActivityService(IActivityRepository activityRepository) {
        super(activityRepository);
    }

}
