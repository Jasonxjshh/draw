package com.Jason.test.domain.strategy;

import com.Jason.domain.strategy.model.entity.RaffleAwardEntity;
import com.Jason.domain.strategy.model.entity.RaffleFactorEntity;
import com.Jason.domain.strategy.service.AbstractRaffleStrategy;
import com.Jason.domain.strategy.service.armory.IStrategyArmory;
import com.Jason.domain.strategy.service.rule.chain.impl.RuleWeightLogicChain;
import com.Jason.domain.strategy.service.rule.tree.impl.RuleLockLogicTreeNode;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: Jason
 * @Date: 2024/10/8 14:59
 * @Description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyRaffleTest {

    @Resource
    private IStrategyArmory strategyArmory;

    @Resource
    private AbstractRaffleStrategy raffleStrategy;

    @Resource
    private RuleLockLogicTreeNode ruleLockLogicTreeNode;
    @Before
    public void setUp() {
        // 策略装配 100001、100002、100003
        log.info("测试结果：{}", strategyArmory.assembleLotteryStrategy(100006L));
        // 通过反射 mock 规则中的值
        ReflectionTestUtils.setField(ruleLockLogicTreeNode, "userRaffleCount", 30L);
    }

    @Test
    public void test_performRaffle() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                    .userId("user001")
                    .strategyId(100006L)
                    .build();
            RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);
            log.info("请求参数：{}", JSON.toJSONString(raffleFactorEntity));
            log.info("测试结果：{}", JSON.toJSONString(raffleAwardEntity));
        }
        new CountDownLatch(1).await();
    }
}
