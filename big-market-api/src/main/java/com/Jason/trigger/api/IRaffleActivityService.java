package com.Jason.trigger.api;

import com.Jason.trigger.api.dto.ActivityDrawRequestDTO;
import com.Jason.trigger.api.dto.ActivityDrawResponseDTO;
import com.Jason.types.model.Response;

/**
 * @Author: Jason
 * @Date: 2024/11/19 23:18
 * @Description:
 */
public interface IRaffleActivityService {
    /**
     * 活动装配，数据预热缓存
     * @param activityId 活动ID
     * @return 装配结果
     */
    Response<Boolean> armory(Long activityId);

    /**
     * 活动抽奖接口
     * @param request 请求对象
     * @return 返回结果
     */
    Response<ActivityDrawResponseDTO> draw(ActivityDrawRequestDTO request);
}
