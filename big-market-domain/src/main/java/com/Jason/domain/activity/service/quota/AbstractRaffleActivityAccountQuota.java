package com.Jason.domain.activity.service.quota;

import com.Jason.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import com.Jason.domain.activity.model.entity.*;
import com.Jason.domain.activity.repository.IActivityRepository;
import com.Jason.domain.activity.service.IRaffleActivityAccountQuotaService;
import com.Jason.domain.activity.service.quota.rule.IActionChain;
import com.Jason.domain.activity.service.quota.rule.factory.DefaultActivityChainFactory;
import com.Jason.types.enums.ResponseCode;
import com.Jason.types.exception.AppException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: Jason
 * @Date: 2024/11/5 21:23
 * @Description:
 */
@Slf4j
public abstract class AbstractRaffleActivityAccountQuota extends RaffleActivityAccountQuotaSupport implements IRaffleActivityAccountQuotaService {
    public AbstractRaffleActivityAccountQuota(IActivityRepository activityRepository, DefaultActivityChainFactory defaultActivityChainFactory) {
        super(activityRepository, defaultActivityChainFactory);
    }

    @Override
    public String createOrder(SkuRechargeEntity skuRechargeEntity) {
        //1. 参数校验
        String userId = skuRechargeEntity.getUserId();
        Long sku = skuRechargeEntity.getSku();
        String outBusinessNo = skuRechargeEntity.getOutBusinessNo();
        if (null == sku || StringUtils.isBlank(userId) || StringUtils.isBlank(outBusinessNo)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }
        //2. 查询基础信息
        //2.1 通过Sku查询活动信息
        ActivitySkuEntity activitySkuEntity = queryActivitySku(sku);
        //2.2 查询活动信息
        ActivityEntity activityEntity = queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
        //2.3 查询次数信息（用户在活动上可参与的次数）
        ActivityCountEntity activityCountEntity = queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());
        //3. 活动动作规则校验 todo 后续处理规则过滤流程，暂时也不处理责任链结果
        IActionChain actionChain = defaultActivityChainFactory.openActionChain();
        actionChain.action(activitySkuEntity, activityEntity, activityCountEntity);
        //4. 构建订单聚合对象
        CreateQuotaOrderAggregate createOrderAggregate = buildOrderAggregate(skuRechargeEntity, activitySkuEntity, activityEntity, activityCountEntity);
        //5. 保存订单
        doSaveOrder(createOrderAggregate);
        //6. 返回单号
        return createOrderAggregate.getActivityOrderEntity().getOrderId();
    }

    protected abstract CreateQuotaOrderAggregate buildOrderAggregate(SkuRechargeEntity skuRechargeEntity, ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity);

    protected abstract void doSaveOrder(CreateQuotaOrderAggregate createOrderAggregate);



}