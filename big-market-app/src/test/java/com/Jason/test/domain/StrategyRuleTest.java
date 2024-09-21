package com.Jason.test.domain;

import com.Jason.domain.strategy.model.entity.RaffleAwardEntity;
import com.Jason.domain.strategy.model.entity.RaffleFactorEntity;
import com.Jason.domain.strategy.service.IRaffleStrategy;
import com.Jason.domain.strategy.service.rule.impl.RuleBlackListLogicFilter;
import com.Jason.domain.strategy.service.rule.impl.RuleWeightLogicFilter;
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
    private RuleBlackListLogicFilter blackListLogicFilter;

    @Resource
    private RuleWeightLogicFilter weightLogicFilter;

    @Before
    public void init() {
        ReflectionTestUtils.setField(weightLogicFilter, "userScore", 4500L);
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

}
