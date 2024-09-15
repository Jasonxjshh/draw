package com.Jason.domain.strategy.service.armory;

import com.Jason.domain.strategy.model.entity.StrategyAwardEntity;
import com.Jason.domain.strategy.repository.IStrategyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;

/**
 * @Author: Jason
 * @Date: 2024-09-14  13:41
 * @Description: 策略装配库, 负责初始化策略计算
 */
@Slf4j
@Service
public class StrategyArmory implements IStrategyArmory{

    @Resource
    private IStrategyRepository strategyRepository;

    public void assembleLotteryStrategy(Long strategyId){
        //查询策略配置
        List<StrategyAwardEntity> strategyAwardEntities = strategyRepository.assembleLotteryStrategy(strategyId);

        //获取概率最小值
        BigDecimal minAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        //获取概率值的总和
        BigDecimal sumAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal rateRange = sumAwardRate.divide(minAwardRate, 0, RoundingMode.CEILING);

        //填充概率奖品表
        ArrayList<Integer> strategyAwardSearchRateTable = new ArrayList<>(rateRange.intValue());
        for (StrategyAwardEntity strategyAwardEntity : strategyAwardEntities) {
            Integer awardId = strategyAwardEntity.getAwardId();
            BigDecimal awardRate = strategyAwardEntity.getAwardRate();
            //rateRange.divide(minAwardRate).multiply(awardRate).setScale(0, RoundingMode.CEILING).intValue();
            for (int i = 0; i < awardRate.divide(minAwardRate, 0, RoundingMode.CEILING).intValue(); i++) {
                strategyAwardSearchRateTable.add(awardId);
            }
        }
        Collections.shuffle(strategyAwardSearchRateTable);

        HashMap<Integer, Integer> shuffleStrategyAwardSearchRateTable = new HashMap<>();

        for (int i = 0; i < strategyAwardSearchRateTable.size(); i++) {
            shuffleStrategyAwardSearchRateTable.put(i, strategyAwardSearchRateTable.get(i));
        }

        strategyRepository.storeStrategyAwardSearchRateTables(strategyId, rateRange, shuffleStrategyAwardSearchRateTable);
    }

    @Override
    public Integer getRandomAwardId(Long strategyId) {
        int rateRange =strategyRepository.getRateRang(strategyId);

        return strategyRepository.getStrategyAwardAssemble(strategyId, new SecureRandom().nextInt(rateRange));
    }


}
