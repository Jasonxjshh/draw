package com.Jason.domain.activity.service;

import com.Jason.domain.activity.model.entity.ActivityOrderEntity;
import com.Jason.domain.activity.model.entity.ActivityShopCartEntity;
import com.Jason.domain.activity.model.entity.SkuRechargeEntity;

/**
 * @Author: Jason
 * @Date: 2024/11/5 21:22
 * @Description: 抽奖活动订单接口
 */
public interface IRaffleActivityAccountQuotaService {


    /**
     * 创建 sku 账户充值订单，给用户增加抽奖次数
     * <p>
     * 1. 在【打卡、签到、分享、对话、积分兑换】等行为动作下，创建出活动订单，给用户的活动账户【日、月】充值可用的抽奖次数。
     * 2. 对于用户可获得的抽奖次数，比如首次进来就有一次，则是依赖于运营配置的动作，在前端页面上。用户点击后，可以获得一次抽奖次数。
     *
     * @param skuRechargeEntity 活动商品充值实体对象
     * @return 活动ID
     */
    String createOrder(SkuRechargeEntity skuRechargeEntity);


    Integer queryRaffleActivityAccountDayPartakeCount(Long activityId, String userId);
}

