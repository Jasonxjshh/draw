package com.Jason.infrastructure.persistent.dao;

import com.Jason.infrastructure.persistent.po.RaffleActivityAccount;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Jason
 * @Date: 2024/11/1 21:35
 * @Description:
 */
@Mapper
public interface IRaffleActivityAccountDao {
    int updateAccountQuota(RaffleActivityAccount raffleActivityAccount);

    void insert(RaffleActivityAccount raffleActivityAccount);
}
