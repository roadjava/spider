package com.threepeople.spider.mapper;

import com.threepeople.spider.bean.dto.EnterpriseDTO;
import com.threepeople.spider.bean.entity.EnterpriseDO;

/**
 * 企业mapper
 */
public interface EnterpriseMapper {
    /**
     * 通过唯一键查询一个企业实体
     */
    EnterpriseDO selectByPrimaryKey(EnterpriseDTO dto);
    /**
     * 保存
     */
    int saveEnterprise(EnterpriseDTO dto);
    /**
     * 通过唯一键更新一个企业实体
     */
    int updateEnterpriseByPrimaryKey(EnterpriseDTO dto);
}
