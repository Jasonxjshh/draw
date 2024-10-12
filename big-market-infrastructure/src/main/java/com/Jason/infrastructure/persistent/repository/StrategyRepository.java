package com.Jason.infrastructure.persistent.repository;

import com.Jason.domain.strategy.model.entity.StrategyAwardEntity;
import com.Jason.domain.strategy.model.entity.StrategyEntity;
import com.Jason.domain.strategy.model.entity.StrategyRuleEntity;
import com.Jason.domain.strategy.model.vo.*;
import com.Jason.domain.strategy.repository.IStrategyRepository;
import com.Jason.infrastructure.persistent.dao.*;
import com.Jason.infrastructure.persistent.po.*;
import com.Jason.infrastructure.persistent.redis.IRedisService;
import com.Jason.types.common.Constants;
import com.Jason.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.Jason.types.enums.ResponseCode.UN_ASSEMBLED_STRATEGY_ARMORY;

/**
 * @Author: Jason
 * @Date: 2024-09-14  13:43
 * @Description: 策略仓储实现
 */
@Repository
@Slf4j
public class StrategyRepository implements IStrategyRepository {

    @Resource
    private IStrategyAwardDao strategyAwardDao;

    @Resource
    private IStrategyRulesDao strategyRulesDao;

    @Resource
    private IStrategyDao strategyDao;

    @Resource
    private IRedisService redisService;

    @Resource
    private IRuleTreeDao ruleTreeDao;

    @Resource
    private IRuleTreeNodeDao ruleTreeNodeDao;

    @Resource
    private IRuleTreeNodeLineDao ruleTreeNodeLineDao;

    @Override
    public List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId) {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_LIST_KEY + strategyId;
        List<StrategyAwardEntity>  strategyAwardEntities = redisService.getValue(cacheKey);
        if (strategyAwardEntities != null && !strategyAwardEntities.isEmpty()) return strategyAwardEntities;

        List<StrategyAward> strategyAwards = strategyAwardDao.queryStrategyAwardByStrategyId(strategyId);
        strategyAwardEntities = new ArrayList<>(strategyAwards.size());
        for (StrategyAward strategyAward : strategyAwards) {
            StrategyAwardEntity strategyAwardEntity = StrategyAwardEntity.builder()
                        .strategyId(strategyAward.getStrategyId())
                        .awardId(strategyAward.getAwardId())
                        .awardTitle(strategyAward.getAwardTitle())
                        .awardSubtitle(strategyAward.getAwardSubtitle())
                        .awardCount(strategyAward.getAwardCount())
                        .awardCountSurplus(strategyAward.getAwardCountSurplus())
                        .awardRate(strategyAward.getAwardRate())
                        .ruleModels(strategyAward.getRuleModels())
                        .sort(strategyAward.getSort())
                        .createTime(strategyAward.getCreateTime())
                        .updateTime(strategyAward.getUpdateTime())
                        .build();
            strategyAwardEntities.add(strategyAwardEntity);
        }
        redisService.setValue(cacheKey, strategyAwardEntities);
        return strategyAwardEntities;
    }

    @Override
    public StrategyEntity queryStrategyEntityByStrategyId(Long strategyId) {
        String cacheKey = Constants.RedisKey.STRATEGY_KEY + strategyId;
        StrategyEntity strategyEntity = redisService.getValue(cacheKey);
        if (strategyEntity != null) return strategyEntity;
        Strategy strategy = strategyDao.queryStrategyByStrategyId(strategyId);
        strategyEntity = StrategyEntity.builder()
                .strategyId(strategy.getStrategyId())
                .strategyDesc(strategy.getStrategyDesc())
                .ruleModels(strategy.getRuleModels())
                .build();
        redisService.setValue(cacheKey, strategyEntity);
        return strategyEntity;
    }

    @Override
    public StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel) {
        String cacheKey = Constants.RedisKey.STRATEGY_RULE_KEY + strategyId + "_" + ruleModel;
        StrategyRuleEntity strategyRuleEntity = redisService.getValue(cacheKey);
        if (strategyRuleEntity != null) return strategyRuleEntity;
        StrategyRule strategyRule = strategyRulesDao.queryStrategyRule(strategyId, ruleModel);
        if (strategyRule != null) {
            strategyRuleEntity = StrategyRuleEntity.builder()
                    .strategyId(strategyRule.getStrategyId())
                    .awardId(strategyRule.getAwardId())
                    .ruleType(strategyRule.getRuleType())
                    .ruleModel(strategyRule.getRuleModel())
                    .ruleValue(strategyRule.getRuleValue())
                    .ruleDesc(strategyRule.getRuleDesc())
                    .build();
            redisService.setValue(cacheKey, strategyRuleEntity);
        }
        return strategyRuleEntity;
    }

    @Override
    public String queryStrategyRuleValue(Long strategyId, String ruleModel) {
        return queryStrategyRuleValue(strategyId, null, ruleModel);
    }

    @Override
    public String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel) {
        StrategyRule strategyRule = new StrategyRule();
        strategyRule.setStrategyId(strategyId);
        strategyRule.setAwardId(awardId);
        strategyRule.setRuleModel(ruleModel);
        return strategyRulesDao.queryStrategyRuleValue(strategyRule);
    }

    @Override
    public StrategyAwardRuleModelVO queryStrategyAwardRuleModel(Long strategyId, Integer awardId) {
        StrategyAward strategyAward = new StrategyAward();
        strategyAward.setStrategyId(strategyId);
        strategyAward.setAwardId(awardId);
        String ruleModels = strategyAwardDao.queryStrategyAwardRuleModel(strategyAward);
        if (ruleModels != null) return StrategyAwardRuleModelVO.builder().ruleModel(ruleModels).build();
        else {
            throw new AppException("策略配置错误");
        }
    }

    @Override
    public void storeStrategyAwardSearchRateTables(String key, BigDecimal rateRange, HashMap<Integer, Integer> shuffleStrategyAwardSearchRateTable) {

        redisService.setValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY+key, rateRange.intValue());

        Map<Integer, Integer> cacheRateTable = redisService.getMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY+key);
        cacheRateTable.putAll(shuffleStrategyAwardSearchRateTable);
    }

    @Override
    public int getRateRange(Long strategyId) {
        return getRateRange(String.valueOf(strategyId));
    }

    @Override
    public int getRateRange(String key) {
        String cacheKey = Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key;
        if (!redisService.isExists(cacheKey)) {
            throw new AppException(UN_ASSEMBLED_STRATEGY_ARMORY.getCode(), cacheKey + Constants.COLON + UN_ASSEMBLED_STRATEGY_ARMORY.getInfo());
        }
        return redisService.getValue(cacheKey);

    }

    @Override
    public Integer getStrategyAwardAssemble(String key, int rateKey) {
        return redisService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY+key, rateKey);
    }

    @Override
    public RuleTreeVO queryRuleTreeVOByTreeId(String treeId) {
        //先读缓存
        String cacheKey = Constants.RedisKey.RULE_TREE_VO_KEY + treeId;
        RuleTreeVO ruleTreeVOCache = redisService.getValue(cacheKey);
        if (ruleTreeVOCache != null) return ruleTreeVOCache;


        RuleTree ruleTree = ruleTreeDao.queryRuleTreeByTreeId(treeId);
        List<RuleTreeNode> ruleTreeNodes = ruleTreeNodeDao.queryRuleTreeNodeListByTreeId(treeId);
        List<RuleTreeNodeLine> ruleTreeNodeLines = ruleTreeNodeLineDao.queryRuleTreeNodeLineListByTreeId(treeId);

        //tree node line 转成Map结构
        Map<String, List<RuleTreeNodeLineVO>> ruleTreeNodeLineMap = new HashMap<>();
        for (RuleTreeNodeLine ruleTreeNodeLine : ruleTreeNodeLines) {
            RuleTreeNodeLineVO ruleTreeNodeLineVO = RuleTreeNodeLineVO.builder()
                    .treeId(ruleTreeNodeLine.getTreeId())
                    .ruleNodeFrom(ruleTreeNodeLine.getRuleNodeFrom())
                    .ruleNodeTo(ruleTreeNodeLine.getRuleNodeTo())
                    .ruleLimitType(RuleLimitTypeVO.valueOf(ruleTreeNodeLine.getRuleLimitType()))
                    .ruleLimitValue(RuleLogicCheckTypeVO.valueOf(ruleTreeNodeLine.getRuleLimitValue()))
                    .build();
            List<RuleTreeNodeLineVO> ruleTreeNodeLineVOList = ruleTreeNodeLineMap.computeIfAbsent(ruleTreeNodeLine.getRuleNodeFrom(), k -> new ArrayList<>());
            ruleTreeNodeLineVOList.add(ruleTreeNodeLineVO);
        }
        //tree node转成Map结构
        Map<String, RuleTreeNodeVO> ruleTreeNodeVOMap = new HashMap<>();
        for (RuleTreeNode ruleTreeNode : ruleTreeNodes) {
            RuleTreeNodeVO ruleTreeNodeVO = RuleTreeNodeVO.builder()
                    .treeId(ruleTreeNode.getTreeId())
                    .ruleKey(ruleTreeNode.getRuleKey())
                    .ruleDesc(ruleTreeNode.getRuleDesc())
                    .ruleValue(ruleTreeNode.getRuleValue())
                    .treeNodeLineVOList(ruleTreeNodeLineMap.get(ruleTreeNode.getRuleKey()))
                    .build();
            ruleTreeNodeVOMap.put(ruleTreeNode.getRuleKey(), ruleTreeNodeVO);
        }
        RuleTreeVO ruleTreeVO = RuleTreeVO.builder()
                .treeId(ruleTree.getTreeId())
                .treeName(ruleTree.getTreeName())
                .treeDesc(ruleTree.getTreeDesc())
                .treeRootRuleNode(ruleTree.getTreeRootRuleKey())
                .treeNodeMap(ruleTreeNodeVOMap)
                .build();
        redisService.setValue(cacheKey, ruleTreeVO);
        return ruleTreeVO;

    }

    @Override
    public void cacheStrategyAwardCount(String cacheKey, Integer awardCount) {
        if (null != redisService.getValue(cacheKey)) return;
        redisService.setAtomicLong(cacheKey, awardCount);
    }

    @Override
    public Boolean subtractionAwardStock(String cacheKey) {
        long decrCount = redisService.decr(cacheKey);
        if (decrCount < 0){
            redisService.setValue(cacheKey, 0);
            return false;
        }
        // setNx 加锁进行兜底
        String lockKey = cacheKey + Constants.UNDER_LINE + decrCount;
        Boolean lock = redisService.setNx(lockKey);
        if (!lock) {
            log.info("策略奖品库存扣减失败: {}" , lockKey);
            return false;
        }
        return true;
    }

    @Override
    public void awardStockConsumeSendQueue(StrategyAwardStockKeyVO build) {
        String cacheKey  = Constants.RedisKey.STRATEGY_AWARD_COUNT_QUEUE_KEY;
        //创建队列信息
        RBlockingQueue<Object> blockingQueue = redisService.getBlockingQueue(cacheKey);
        //增加到延迟队列中
        RDelayedQueue<Object> delayedQueue = redisService.getDelayedQueue(blockingQueue);
        delayedQueue.offer(build, 5, TimeUnit.SECONDS);
    }

    @Override
    public StrategyAwardStockKeyVO takeQueueValue() throws InterruptedException{
        String cacheKey  = Constants.RedisKey.STRATEGY_AWARD_COUNT_QUEUE_KEY;
        RBlockingQueue<StrategyAwardStockKeyVO> blockingQueue = redisService.getBlockingQueue(cacheKey);
        return blockingQueue.poll();
    }

    @Override
    public void updateStrategyAwardStock(Long strategyId, Integer awardId) {
        StrategyAward strategyAward = new StrategyAward();
        strategyAward.setStrategyId(strategyId);
        strategyAward.setAwardId(awardId);
        strategyAwardDao.updateStrategyAwardStock(strategyAward);

    }

    @Override
    public StrategyAwardEntity queryStrategyAwardEntity(Long strategyId, Integer awardId) {
        // 优先从缓存获取
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_KEY + strategyId + Constants.UNDER_LINE + awardId;
        StrategyAwardEntity strategyAwardEntity = redisService.getValue(cacheKey);
        if (null != strategyAwardEntity) return strategyAwardEntity;
        // 查询数据
        StrategyAward strategyAwardReq = new StrategyAward();
        strategyAwardReq.setStrategyId(strategyId);
        strategyAwardReq.setAwardId(awardId);
        StrategyAward strategyAwardRes = strategyAwardDao.queryStrategyAward(strategyAwardReq);
        // 转换数据
        strategyAwardEntity = StrategyAwardEntity.builder()
                .strategyId(strategyAwardRes.getStrategyId())
                .awardId(strategyAwardRes.getAwardId())
                .awardTitle(strategyAwardRes.getAwardTitle())
                .awardSubtitle(strategyAwardRes.getAwardSubtitle())
                .awardCount(strategyAwardRes.getAwardCount())
                .awardCountSurplus(strategyAwardRes.getAwardCountSurplus())
                .awardRate(strategyAwardRes.getAwardRate())
                .sort(strategyAwardRes.getSort())
                .build();
        // 缓存结果
        redisService.setValue(cacheKey, strategyAwardEntity);
        // 返回数据
        return strategyAwardEntity;

    }


}
