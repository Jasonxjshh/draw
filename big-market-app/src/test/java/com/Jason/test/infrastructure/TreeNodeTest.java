package com.Jason.test.infrastructure;

import com.Jason.domain.strategy.model.vo.RuleTreeVO;
import com.Jason.domain.strategy.repository.IStrategyRepository;
import com.Jason.infrastructure.persistent.dao.IRuleTreeDao;
import com.Jason.infrastructure.persistent.dao.IRuleTreeNodeDao;
import com.Jason.infrastructure.persistent.dao.IRuleTreeNodeLineDao;
import com.Jason.infrastructure.persistent.po.RuleTree;
import com.Jason.infrastructure.persistent.po.RuleTreeNode;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Jason
 * @Date: 2024/10/6 14:13
 * @Description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TreeNodeTest {
    @Resource
    private IStrategyRepository strategyRepository;
    @Test
    public void test_treeNode() {

        RuleTreeVO ruleTreeVO = strategyRepository.queryRuleTreeVOByTreeId("tree_lock");
        log.info("ruleTreeVO:{}", JSON.toJSONString(ruleTreeVO));
    }
}
