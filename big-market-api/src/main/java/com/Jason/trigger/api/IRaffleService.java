package com.Jason.trigger.api;

import com.Jason.trigger.dto.RaffleAwardListRequestDTO;
import com.Jason.trigger.dto.RaffleAwardListResponseDTO;
import com.Jason.trigger.dto.RaffleRequestDTO;
import com.Jason.trigger.dto.RaffleResponseDTO;
import com.Jason.types.model.Response;

import java.util.List;

/**
 * @Author: Jason
 * @Date: 2024/10/10 15:46
 * @Description: 抽奖服务api接口
 */
public interface IRaffleService {
    /**
     * 抽奖策略装配接口
     * @param strategyId 策略id
     * @return response
     */
    Response<Boolean> strategyArmory(Long strategyId);

    /**
     * 查询抽奖奖品列表配置
     *
     * @param requestDTO 抽奖奖品列表查询请求参数
     * @return 奖品列表数据
     */
    Response<List<RaffleAwardListResponseDTO>> queryRaffleAwardList(RaffleAwardListRequestDTO requestDTO);

    /**
     * 随机抽奖接口
     *
     * @param requestDTO 请求参数
     * @return 抽奖结果
     */
    Response<RaffleResponseDTO> randomRaffle(RaffleRequestDTO requestDTO);


}
