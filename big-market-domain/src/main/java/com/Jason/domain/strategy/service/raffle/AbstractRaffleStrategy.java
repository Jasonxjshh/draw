package com.Jason.domain.strategy.service.raffle;

import com.Jason.domain.strategy.model.entity.RaffleAwardEntity;
import com.Jason.domain.strategy.model.entity.RaffleFactorEntity;
import com.Jason.domain.strategy.model.entity.RuleActionEntity;
import com.Jason.domain.strategy.model.entity.StrategyEntity;
import com.Jason.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.Jason.domain.strategy.model.vo.StrategyAwardRuleModelVO;
import com.Jason.domain.strategy.repository.IStrategyRepository;
import com.Jason.domain.strategy.service.IRaffleStrategy;
import com.Jason.domain.strategy.service.armory.IStrategyDispatch;
import com.Jason.domain.strategy.service.rule.filter.factory.DefaultLogicFactory;
import com.Jason.types.enums.ResponseCode;
import com.Jason.types.exception.AppException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @Author: Jason
 * @Date: 2024-09-19  14:42
 * @Description: 抽奖策略抽象类
 */
public abstract class AbstractRaffleStrategy implements IRaffleStrategy {
    private static final Logger log = LoggerFactory.getLogger(AbstractRaffleStrategy.class);
    @Resource
    private IStrategyRepository repository;

    @Resource
    private IStrategyDispatch strategyDispatch;


    @Override
    public  RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactor) {
        //1. 参数校验
        String userId = raffleFactor.getUserId();
        Long strategyId = raffleFactor.getStrategyId();
        if (StringUtils.isBlank(userId) || strategyId == null) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        //2. 策略查询
        StrategyEntity strategyEntity = repository.queryStrategyEntityByStrategyId(strategyId);

        //3. 抽奖前 ---- 规则过滤
        if (strategyEntity.getRuleModels() != null){
            RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> raffleBeforeRuleAction = this.doCheckRaffleBeforeLogic(
                    raffleFactor, strategyEntity.ruleModels());
            if (RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(raffleBeforeRuleAction.getCode())){

                //如果是被黑名单接管的话先执行黑名单规则
                if (DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode().equals(raffleBeforeRuleAction.getRuleModel())){
                    return RaffleAwardEntity.builder()
                            .awardId(raffleBeforeRuleAction.getData().getAwardId())
                            .build();
                } else if (DefaultLogicFactory.LogicModel.RULE_WIGHT.getCode().equals(raffleBeforeRuleAction.getRuleModel())) {
                    Integer randomAwardId = strategyDispatch.getRandomAwardId(strategyId, raffleBeforeRuleAction.getData().getRuleWeightValueKey());
                    return RaffleAwardEntity.builder()
                            .awardId(randomAwardId)
                            .build();
                }
            }
        }


        //4. 正常抽奖流程
        Integer awardId = strategyDispatch.getRandomAwardId(strategyId);

        //5. 抽奖中 ---- 规则过滤
        //6. 查询奖品的抽奖规则（抽奖中，抽奖后）
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = repository.queryStrategyAwardRuleModel(strategyId, awardId);
        RuleActionEntity<RuleActionEntity.RaffleCenterEntity> raffleCenterRuleAction = this.doCheckRaffleCenterLogic(
                RaffleFactorEntity.builder()
                        .userId(userId)
                        .strategyId(strategyId)
                        .awardId(awardId).build(), strategyAwardRuleModelVO.raffleCenterRuleModels());
        if (RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(raffleCenterRuleAction.getCode())){
            log.info("中奖中规则拦截，通过抽奖后规则 rule_luck_award 走兜底奖励");
            this.doCheckRaffleAfterLogic(RaffleFactorEntity.builder()
                    .userId(userId)
                    .strategyId(strategyId)
                    .awardId(awardId).build(), strategyAwardRuleModelVO.raffleAfterRuleModels());
            return RaffleAwardEntity.builder()
                    .awardId(awardId)
                    .awardDesc("中奖中规则拦截，通过抽奖后规则 rule_luck_award 走兜底奖励")
                    .build();
        }

        return RaffleAwardEntity.builder().awardId(awardId).build();
    }

    protected abstract RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> doCheckRaffleBeforeLogic(RaffleFactorEntity raffleFactor, String ...logics);
    protected abstract RuleActionEntity<RuleActionEntity.RaffleCenterEntity> doCheckRaffleCenterLogic(RaffleFactorEntity raffleFactor, String ...logics);
    protected abstract RuleActionEntity<RuleActionEntity.RaffleAfterEntity> doCheckRaffleAfterLogic(RaffleFactorEntity raffleFactor, String ...logics);

}
