package com.Jason.domain.activity.repository;

import com.Jason.domain.activity.model.aggregate.CreatePartakeOrderAggregate;
import com.Jason.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import com.Jason.domain.activity.model.entity.*;
import com.Jason.domain.activity.model.valobj.ActivitySkuStockKeyVO;

import java.util.Date;
import java.util.List;

/**
 * @Author: Jason
 * @Date: 2024/11/5 21:21
 * @Description: 活动仓储接口
 */

public interface IActivityRepository {

    ActivitySkuEntity queryActivitySku(Long sku);

    ActivityEntity queryRaffleActivityByActivityId(Long activityId);

    ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId);

    void cacheActivitySkuStockCount(String cacheKey, Integer stockCount);

    boolean subtractionActivitySkuStock(Long sku, String cacheKey, Date endDateTime);

    void activitySkuStockConsumeSendQueue(ActivitySkuStockKeyVO build);

    ActivitySkuStockKeyVO takeQueueValue(Long sku);

    void clearQueueValue(Long sku);

    void updateActivitySkuStock(Long sku);

    void clearActivitySkuStock(Long sku);

    UserRaffleOrderEntity queryNoUsedRaffleOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity);

    ActivityAccountEntity queryActivityAccountByUserId(String userId, Long activityId);

    void saveCreatePartakeOrderAggregate(CreatePartakeOrderAggregate createPartakeOrderAggregate);

    ActivityAccountMonthEntity queryActivityAccountMonthByUserId(String userId, Long activityId, String month);

    ActivityAccountDayEntity queryActivityAccountDayByUserId(String userId, Long activityId, String day);

    List<ActivitySkuEntity> queryActivitySkuListByActivityId(Long activityId);

    Integer queryRaffleActivityAccountDayPartakeCount(Long activityId, String userId);

    List<Long> querySkuList();

    ActivityAccountEntity queryActivityAccountEntity(Long activityId, String userId);

    Integer queryRaffleActivityAccountPartakeCount(Long activityId, String userId);

    UnpaidActivityOrderEntity queryUnpaidActivityOrder(SkuRechargeEntity skuRechargeEntity);

    void doSaveCreditPayOrder(CreateQuotaOrderAggregate createQuotaOrderAggregate);

    void doSaveNoPayOrder(CreateQuotaOrderAggregate createQuotaOrderAggregate);

    void updateOrder(DeliveryOrderEntity deliveryOrderEntity);

    List<SkuProductEntity> querySkuProductEntityListByActivityId(Long activityId);
}

