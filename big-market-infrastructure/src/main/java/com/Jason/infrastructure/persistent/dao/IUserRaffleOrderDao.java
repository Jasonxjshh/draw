package com.Jason.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import com.Jason.infrastructure.persistent.po.UserRaffleOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Jason
 * @Date: 2024/11/15 11:22
 * @Description: 用户抽奖订单表
 */
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IUserRaffleOrderDao {

    @DBRouter
    void insert(UserRaffleOrder userRaffleOrder);
    @DBRouter
    UserRaffleOrder queryNoUsedRaffleOrder(UserRaffleOrder userRaffleOrderReq);
    @DBRouter
    int updateUserRaffleOrderStateUsed(UserRaffleOrder userRaffleOrderReq);
}
