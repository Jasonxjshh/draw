package com.Jason.test.domain.strategy;

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
 * @Date: 2024-09-19  10:40
 * @Description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyTest {
    @Resource
    private StrategyArmoryDispatch strategyArmoryDispatch;

    @Test
    public void test_assembleLotteryStrategy(){
        strategyArmoryDispatch.assembleLotteryStrategy(100001L);

    }

    @Test
    public void test_getRandomAwardId(){
        for (int i = 0; i < 10; i++) {
            log.info("测试结果: 第{}轮", i+1);
            log.info("测试结果: {} - 奖品ID值", strategyArmoryDispatch.getRandomAwardId(100001L));
            log.info("测试结果: {} - 奖品ID值", strategyArmoryDispatch.getRandomAwardId(100001L));
            log.info("测试结果: {} - 奖品ID值", strategyArmoryDispatch.getRandomAwardId(100001L));
        }
    }

    @Test
    public void test_getRandomAwardId_2(){
        for (int i = 0; i < 10; i++) {
            log.info("测试结果: 第{}轮", i+1);
            log.info("测试结果4000策略: {} - 奖品ID值", strategyArmoryDispatch.getRandomAwardId(100001L, "4000:102,103,104,105"));
            log.info("测试结果5000策略: {} - 奖品ID值", strategyArmoryDispatch.getRandomAwardId(100001L, "5000:102,103,104,105,106,107"));
            log.info("测试结果6000策略: {} - 奖品ID值", strategyArmoryDispatch.getRandomAwardId(100001L, "6000:102,103,104,105,106,107,108,109"));
        }
    }



}
