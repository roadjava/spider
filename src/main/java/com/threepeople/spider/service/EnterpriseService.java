package com.threepeople.spider.service;

import com.threepeople.spider.bean.dto.EnterpriseDTO;
import com.threepeople.spider.bean.entity.EnterpriseDO;

/**
 *
 */
public interface EnterpriseService {
    /**
     * 处理传入的企业对象dto:
     * selectByPrimaryKey > 0 ? updateEnterpriseByPrimaryKey : saveEnterprise
     */
    void process(EnterpriseDTO dto);
}
