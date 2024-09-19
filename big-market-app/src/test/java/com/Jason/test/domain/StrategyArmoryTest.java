package com.Jason.test.domain;

import com.Jason.domain.strategy.service.armory.IStrategyArmory;
import com.Jason.domain.strategy.service.armory.StrategyArmoryDispatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: Jason
 * @Date: 2024-09-14  16:29
 * @Description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyArmoryTest {

    @Resource
    private StrategyArmoryDispatch strategyArmory;

    @Before
    public void test_assembleLotteryStrategy(){
        strategyArmory.assembleLotteryStrategy(100001L);

    }

    @Test
    public void test_getRandomAwardId(){
        for (int i = 0; i < 10; i++) {
            log.info("测试结果: 第{}轮", i+1);
            log.info("测试结果: {} - 奖品ID值", strategyArmory.getRandomAwardId(100002L));
            log.info("测试结果: {} - 奖品ID值", strategyArmory.getRandomAwardId(100002L));
            log.info("测试结果: {} - 奖品ID值", strategyArmory.getRandomAwardId(100002L));
        }

    }

}
