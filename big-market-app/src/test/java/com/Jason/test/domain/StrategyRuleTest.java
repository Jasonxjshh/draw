package com.Jason.test.domain;

import com.Jason.domain.strategy.model.entity.RaffleAwardEntity;
import com.Jason.domain.strategy.model.entity.RaffleFactorEntity;
import com.Jason.domain.strategy.service.IRaffleStrategy;
import com.Jason.domain.strategy.service.armory.IStrategyArmory;
import com.Jason.domain.strategy.service.rule.filter.impl.RuleLockLogicFilter;
import com.Jason.domain.strategy.service.rule.filter.impl.RuleWeightLogicFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;

/**
 * @Author: Jason
 * @Date: 2024-09-21  14:32
 * @Description: 抽奖策略测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyRuleTest {
    @Resource
    private IRaffleStrategy raffleStrategy;

    @Resource
    private IStrategyArmory strategyArmory;

    @Resource
    private RuleWeightLogicFilter weightLogicFilter;

    @Resource
    private RuleLockLogicFilter lockLogicFilter;



    @Before
    public void init() {
        ReflectionTestUtils.setField(weightLogicFilter, "userScore", 4500L);
        ReflectionTestUtils.setField(lockLogicFilter, "userRaffleCount", 1L);
        strategyArmory.assembleLotteryStrategy(100001L);
        strategyArmory.assembleLotteryStrategy(100003L);
    }


    @Test
    public void test_rule_weight() {
        RaffleFactorEntity raffleFactor = RaffleFactorEntity.builder()
                .userId("Jason")
                .strategyId(100001L)
                .build();

        RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactor);
        log.info("测试参数：{}", raffleFactor);
        log.info("测试结果：{}", raffleAwardEntity);
    }

    @Test
    public void test_rule_blacklist() {
        RaffleFactorEntity raffleFactor = RaffleFactorEntity.builder()
                .userId("user001")
                .strategyId(100001L)
                .build();
        RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactor);
        log.info("测试参数：{}", raffleFactor);
        log.info("测试结果：{}", raffleAwardEntity);
    }

    @Test
    public void test_rule_lock() {
        RaffleFactorEntity raffleFactor = RaffleFactorEntity.builder()
                .userId("jason")
                .strategyId(100003L)
                .build();

        for (int i = 0; i < 10; i++) {
            RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactor);
            log.info("测试参数：{}", raffleFactor);
            log.info("测试结果：{}", raffleAwardEntity);
        }

    }

}
