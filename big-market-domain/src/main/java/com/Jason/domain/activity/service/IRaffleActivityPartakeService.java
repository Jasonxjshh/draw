package com.Jason.domain.activity.service;


import com.Jason.domain.activity.model.entity.PartakeRaffleActivityEntity;
import com.Jason.domain.activity.model.entity.UserRaffleOrderEntity;

/**
 * @Author: Jason
 * @Date: 2024/11/15 11:50
 * @Description:
 */
public interface IRaffleActivityPartakeService {
    /**
     * 创建抽奖单；用户参与抽奖活动，扣减活动账户库存，产生抽奖单。如存在未被使用的抽奖单则直接返回已存在的抽奖单。
     *
     * @param partakeRaffleActivityEntity 参与抽奖活动实体对象
     * @return 用户抽奖订单实体对象
     */

    UserRaffleOrderEntity createOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity);
}
