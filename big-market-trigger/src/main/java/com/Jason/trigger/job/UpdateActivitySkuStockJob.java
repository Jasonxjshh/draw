package com.Jason.trigger.job;

import com.Jason.domain.activity.model.valobj.ActivitySkuStockKeyVO;
import com.Jason.domain.activity.service.IRaffleActivitySkuStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: Jason
 * @Date: 2024/11/11 17:06
 * @Description:
 */
@Slf4j
@Component
public class UpdateActivitySkuStockJob {

    @Resource
    private IRaffleActivitySkuStockService skuStock;

    @Resource
    private ThreadPoolExecutor executor;


    @Scheduled(cron = "0/5 * * * * ?")
    public void exec() {
        try {
            List<Long> skuList = skuStock.querySkuList();
            for (Long sku : skuList) {
                executor.execute(() -> {
                    ActivitySkuStockKeyVO activitySkuStockKeyVO = null;
                    try {
                        activitySkuStockKeyVO = skuStock.takeQueueValue(sku);
                    } catch (InterruptedException e) {
                        log.error("定时任务，更新活动sku库存失败 userId: {} topic: {}", activitySkuStockKeyVO.getSku(), activitySkuStockKeyVO.getActivityId());
                    }
                    if (null == activitySkuStockKeyVO) return;
                    log.info("定时任务，更新活动sku库存 sku:{} activityId:{}", activitySkuStockKeyVO.getSku(), activitySkuStockKeyVO.getActivityId());
                    skuStock.updateActivitySkuStock(activitySkuStockKeyVO.getSku());
                });
            }

        } catch (Exception e) {
            log.error("定时任务，更新活动sku库存失败", e);
        }
    }

}

