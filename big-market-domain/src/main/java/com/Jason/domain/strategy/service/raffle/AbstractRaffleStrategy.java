package com.Jason.domain.strategy.service.raffle;

import com.Jason.domain.strategy.model.entity.RaffleAwardEntity;
import com.Jason.domain.strategy.model.entity.RaffleFactorEntity;
import com.Jason.domain.strategy.model.entity.RuleActionEntity;
import com.Jason.domain.strategy.model.entity.StrategyEntity;
import com.Jason.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.Jason.domain.strategy.repository.IStrategyRepository;
import com.Jason.domain.strategy.service.IRaffleStrategy;
import com.Jason.domain.strategy.service.armory.IStrategyDispatch;
import com.Jason.domain.strategy.service.rule.factory.DefaultLogicFactory;
import com.Jason.types.enums.ResponseCode;
import com.Jason.types.exception.AppException;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;

/**
 * @Author: Jason
 * @Date: 2024-09-19  14:42
 * @Description: 抽奖策略抽象类
 */
public abstract class AbstractRaffleStrategy implements IRaffleStrategy {
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
        return RaffleAwardEntity.builder().awardId(strategyDispatch.getRandomAwardId(strategyId)).build();
    }

    protected abstract RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> doCheckRaffleBeforeLogic(RaffleFactorEntity raffleFactor, String ...logics);

}
