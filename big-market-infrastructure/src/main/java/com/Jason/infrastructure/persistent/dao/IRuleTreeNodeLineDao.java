package com.Jason.infrastructure.persistent.dao;

import com.Jason.infrastructure.persistent.po.RuleTreeNodeLine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: Jason
 * @Date: 2024/10/6 13:55
 * @Description:
 */
@Mapper
public interface IRuleTreeNodeLineDao {
    List<RuleTreeNodeLine> queryRuleTreeNodeLineListByTreeId(String treeId);
}
