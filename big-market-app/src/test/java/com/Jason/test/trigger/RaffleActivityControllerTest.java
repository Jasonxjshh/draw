package com.Jason.test.trigger;

import com.Jason.domain.activity.service.armory.IActivityArmory;
import com.Jason.trigger.api.IRaffleActivityService;
import com.Jason.types.model.Response;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: Jason
 * @Date: 2024/11/28 12:12
 * @Description:
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleActivityControllerTest {

    @Resource
    private IRaffleActivityService raffleActivityService;

    @Resource
    private IActivityArmory activityArmory;

    @Before
    public void setUp() {
        log.info("装配活动：{}", activityArmory.assembleActivitySku(9011L));
    }
    @Test
    public void test_calendarSignRebate() throws InterruptedException {
        Response<Boolean> response = raffleActivityService.calendarSignRebate("Jason");
        log.info("测试结果：{}", JSON.toJSONString(response));
        new CountDownLatch(1).await();
    }
}
