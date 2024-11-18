package com.Jason.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import com.Jason.infrastructure.persistent.po.RaffleActivityAccount;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Jason
 * @Date: 2024/11/1 21:35
 * @Description:
 */
@Mapper
public interface IRaffleActivityAccountDao {
    @DBRouter(key = "userId")
    int updateAccountQuota(RaffleActivityAccount raffleActivityAccount);

    @DBRouter(key = "userId")
    void insert(RaffleActivityAccount raffleActivityAccount);
    @DBRouter(key = "userId")
    RaffleActivityAccount queryActivityAccountByUserId(RaffleActivityAccount raffleActivityAccountReq);

    int updateActivityAccountSubtractionQuota(RaffleActivityAccount build);

    int updateActivityAccountMonthSurplusImageQuota(RaffleActivityAccount build);

    int updateActivityAccountDaySurplusImageQuota(RaffleActivityAccount build);
}
