package com.Jason.domain.strategy.service;

import com.Jason.domain.strategy.model.entity.RaffleAwardEntity;
import com.Jason.domain.strategy.model.entity.RaffleFactorEntity;
import com.Jason.domain.strategy.model.entity.RuleActionEntity;
import com.Jason.domain.strategy.model.entity.StrategyAwardEntity;
import com.Jason.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.Jason.domain.strategy.model.vo.StrategyAwardRuleModelVO;
import com.Jason.domain.strategy.repository.IStrategyRepository;
import com.Jason.domain.strategy.service.armory.IStrategyDispatch;
import com.Jason.domain.strategy.service.rule.chain.ILogicChain;
import com.Jason.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.Jason.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.Jason.types.enums.ResponseCode;
import com.Jason.types.exception.AppException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: Jason
 * @Date: 2024-09-19  14:42
 * @Description: 抽奖策略抽象类
 */
public abstract class AbstractRaffleStrategy implements IRaffleStrategy{
    private static final Logger log = LoggerFactory.getLogger(AbstractRaffleStrategy.class);
    // 策略仓储服务 -> domain层像一个大厨，仓储层提供米面粮油
    @Resource
    protected IStrategyRepository repository;
    // 策略调度服务 -> 只负责抽奖处理，通过新增接口的方式，隔离职责，不需要使用方关心或者调用抽奖的初始化
    @Resource
    protected IStrategyDispatch strategyDispatch;
    // 抽奖的责任链 -> 从抽奖的规则中，解耦出前置规则为责任链处理
    @Resource
    protected DefaultChainFactory chainFactory;
    // 抽奖的决策树 -> 负责抽奖中到抽奖后的规则过滤，如抽奖到A奖品ID，之后要做次数的判断和库存的扣减等。
    @Resource
    protected DefaultTreeFactory treeFactory;

    @Override
    public  RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactor) {
        //1. 参数校验
        String userId = raffleFactor.getUserId();
        Long strategyId = raffleFactor.getStrategyId();
        if (StringUtils.isBlank(userId) || strategyId == null) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        // 2. 责任链抽奖计算【这步拿到的是初步的抽奖ID，之后需要根据ID处理抽奖】注意；黑名单、权重等非默认抽奖的直接返回抽奖结果
        DefaultChainFactory.StrategyAwardVO chainStrategyAwardVO = raffleLogicChain(userId, strategyId);
        log.info("抽奖策略计算-责任链 {} {} {} {}", userId, strategyId, chainStrategyAwardVO.getAwardId(), chainStrategyAwardVO.getLogicModel());
        if (!DefaultChainFactory.LogicModel.RULE_DEFAULT.getCode().equals(chainStrategyAwardVO.getLogicModel())) {
            return buildRaffleAwardEntity(strategyId, chainStrategyAwardVO.getAwardId(), null);
        }

        // 3. 规则树抽奖过滤【奖品ID，会根据抽奖次数判断、库存判断、兜底兜里返回最终的可获得奖品信息】
        DefaultTreeFactory.StrategyAwardVO treeStrategyAwardVO = raffleLogicTree(userId, strategyId, chainStrategyAwardVO.getAwardId(), raffleFactor.getEndDateTime());
        log.info("抽奖策略计算-规则树 {} {} {} {}", userId, strategyId, treeStrategyAwardVO.getAwardId(), treeStrategyAwardVO.getAwardRuleValue());

        // 4. 返回抽奖结果

        return buildRaffleAwardEntity(strategyId, treeStrategyAwardVO.getAwardId(), treeStrategyAwardVO.getAwardRuleValue());
    }

    private RaffleAwardEntity buildRaffleAwardEntity(Long strategyId, Integer awardId, String awardConfig) {
        StrategyAwardEntity strategyAward = repository.queryStrategyAwardEntity(strategyId, awardId);
        return RaffleAwardEntity.builder()
                .awardId(awardId)
                .awardTitle(strategyAward.getAwardTitle())
                .awardConfig(awardConfig)
                .sort(strategyAward.getSort())
                .build();
    }


//        //2. 策略查询
//        StrategyEntity strategyEntity = repository.queryStrategyEntityByStrategyId(strategyId);
//
//        //3. 抽奖前 ---- 规则过滤
//        if (strategyEntity.getRuleModels() != null){
//            RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> raffleBeforeRuleAction = this.doCheckRaffleBeforeLogic(
//                    raffleFactor, strategyEntity.ruleModels());
//            if (RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(raffleBeforeRuleAction.getCode())){
//
//                //如果是被黑名单接管的话先执行黑名单规则
//                if (DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode().equals(raffleBeforeRuleAction.getRuleModel())){
//                    return RaffleAwardEntity.builder()
//                            .awardId(raffleBeforeRuleAction.getData().getAwardId())
//                            .build();
//                } else if (DefaultLogicFactory.LogicModel.RULE_WIGHT.getCode().equals(raffleBeforeRuleAction.getRuleModel())) {
//                    Integer randomAwardId = strategyDispatch.getRandomAwardId(strategyId, raffleBeforeRuleAction.getData().getRuleWeightValueKey());
//                    return RaffleAwardEntity.builder()
//                            .awardId(randomAwardId)
//                            .build();
//                }
//            }
//        }

//        // 将代码进行重构, 以责任链的方式进行
//        ILogicChain logicChain = chainFactory.openLogicChain(strategyId);
//        Integer awardId = logicChain.logic(userId, strategyId);
//
//
//        //5. 抽奖中 ---- 规则过滤
//        //6. 查询奖品的抽奖规则（抽奖中，抽奖后）
//        StrategyAwardRuleModelVO strategyAwardRuleModelVO = repository.queryStrategyAwardRuleModel(strategyId, awardId);
//
//        RuleActionEntity<RuleActionEntity.RaffleCenterEntity> raffleCenterRuleAction = this.doCheckRaffleCenterLogic(
//                RaffleFactorEntity.builder()
//                        .userId(userId)
//                        .strategyId(strategyId)
//                        .awardId(awardId).build(), strategyAwardRuleModelVO.raffleCenterRuleModels());
//        if (RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(raffleCenterRuleAction.getCode())){
//            log.info("中奖中规则拦截，通过抽奖后规则 rule_luck_award 走兜底奖励");
//            this.doCheckRaffleAfterLogic(RaffleFactorEntity.builder()
//                    .userId(userId)
//                    .strategyId(strategyId)
//                    .awardId(awardId).build(), strategyAwardRuleModelVO.raffleAfterRuleModels());
//            return RaffleAwardEntity.builder()
//                    .awardId(awardId)
//                    .awardDesc("中奖中规则拦截，通过抽奖后规则 rule_luck_award 走兜底奖励")
//                    .build();
//        }

    public abstract DefaultChainFactory.StrategyAwardVO raffleLogicChain(String userId, Long strategyId);

    /**
     * 抽奖结果过滤，决策树抽象方法
     *
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return 过滤结果【奖品ID，会根据抽奖次数判断、库存判断、兜底兜里返回最终的可获得奖品信息】
     */
    public abstract DefaultTreeFactory.StrategyAwardVO raffleLogicTree(String userId, Long strategyId, Integer awardId);

    /**
     * 抽奖结果过滤，决策树抽象方法
     *
     * @param userId      用户ID
     * @param strategyId  策略ID
     * @param awardId     奖品ID
     * @param endDateTime 活动结束时间 - 用于设定缓存有效期
     * @return 过滤结果【奖品ID，会根据抽奖次数判断、库存判断、兜底兜里返回最终的可获得奖品信息】
     */
    public abstract DefaultTreeFactory.StrategyAwardVO raffleLogicTree(String userId, Long strategyId, Integer awardId, Date endDateTime);

}
