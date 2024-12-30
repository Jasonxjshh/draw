package com.Jason.test.domain.activity;

import com.Jason.domain.activity.model.entity.SkuRechargeEntity;
import com.Jason.domain.activity.model.entity.UnpaidActivityOrderEntity;
import com.Jason.domain.activity.model.valobj.OrderTradeTypeVO;
import com.Jason.domain.activity.service.IRaffleActivityAccountQuotaService;
import com.Jason.domain.activity.service.armory.IActivityArmory;
import com.Jason.domain.award.model.entity.UserAwardRecordEntity;
import com.Jason.domain.award.model.valobj.AwardStateVO;
import com.Jason.domain.award.service.IAwardService;
import com.Jason.types.exception.AppException;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: Jason
 * @Date: 2024/11/5 22:04
 * @Description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest

public class RaffleActivityAccountQuotaServiceTest {
    @Resource
    private IRaffleActivityAccountQuotaService raffleOrder;

    @Resource
    private IActivityArmory activityArmory;


    @Resource
    private IAwardService awardService;
    @Resource
    private IRaffleActivityAccountQuotaService raffleActivityAccountQuotaService;
    @Before
    public void setUp() {
        log.info("装配活动：{}", activityArmory.assembleActivitySku(9011L));
    }



    @Test
    public void test_createSkuRechargeOrder_duplicate() {
        SkuRechargeEntity skuRechargeEntity = new SkuRechargeEntity();
        skuRechargeEntity.setUserId("xiaofuge");
        skuRechargeEntity.setSku(9011L);
        // outBusinessNo 作为幂等仿重使用，同一个业务单号2次使用会抛出索引冲突 Duplicate entry '700091009111' for key 'uq_out_business_no' 确保唯一性。
        skuRechargeEntity.setOutBusinessNo("700091009112");
        skuRechargeEntity.setOrderTradeType(OrderTradeTypeVO.rebate_no_pay_trade);
        UnpaidActivityOrderEntity unpaidActivityOrder = raffleActivityAccountQuotaService.createOrder(skuRechargeEntity);
        log.info("测试结果：{}", JSON.toJSONString(unpaidActivityOrder));
    }


    /**
     * 测试库存消耗和最终一致更新
     * 1. raffle_activity_sku 库表库存可以设置20个
     * 2. 清空 redis 缓存 flushall
     * 3. for 循环20次，消耗完库存，最终数据库剩余库存为0
     */
    @Test
    public void test_createSkuRechargeOrder() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            try {
                SkuRechargeEntity skuRechargeEntity = new SkuRechargeEntity();
                skuRechargeEntity.setUserId("Jason");
                skuRechargeEntity.setSku(9011L);
                // outBusinessNo 作为幂等仿重使用，同一个业务单号2次使用会抛出索引冲突 Duplicate entry '700091009111' for key 'uq_out_business_no' 确保唯一性。
                skuRechargeEntity.setOutBusinessNo(RandomStringUtils.randomNumeric(12));
                skuRechargeEntity.setOrderTradeType(OrderTradeTypeVO.rebate_no_pay_trade);
                UnpaidActivityOrderEntity unpaidActivityOrder = raffleActivityAccountQuotaService.createOrder(skuRechargeEntity);
                log.info("测试结果：{}", JSON.toJSONString(unpaidActivityOrder));
            } catch (AppException e) {
                log.warn(e.getInfo());
            }
        }

        new CountDownLatch(1).await();
    }

    @Test
    public void test_saveUserAwardRecord() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            UserAwardRecordEntity userAwardRecordEntity = new UserAwardRecordEntity();
            userAwardRecordEntity.setUserId("Jason");
            userAwardRecordEntity.setActivityId(100301L);
            userAwardRecordEntity.setStrategyId(100006L);
            userAwardRecordEntity.setOrderId(RandomStringUtils.randomNumeric(12));
            userAwardRecordEntity.setAwardId(101);
            userAwardRecordEntity.setAwardTitle("OpenAI 增加使用次数");
            userAwardRecordEntity.setAwardTime(new Date());
            userAwardRecordEntity.setAwardState(AwardStateVO.create);
            awardService.saveUserAwardRecord(userAwardRecordEntity);
            Thread.sleep(500);
        }
        new CountDownLatch(1).await();
    }

}
