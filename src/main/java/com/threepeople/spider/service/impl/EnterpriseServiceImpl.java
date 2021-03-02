package com.threepeople.spider.service.impl;

import com.threepeople.spider.bean.dto.EnterpriseDTO;
import com.threepeople.spider.bean.entity.EnterpriseDO;
import com.threepeople.spider.mapper.EnterpriseMapper;
import com.threepeople.spider.service.EnterpriseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 *
 */
@Service
@Slf4j
public class EnterpriseServiceImpl implements EnterpriseService {

    @Resource
    private EnterpriseMapper enterpriseMapper;

    @Override
    public void process(EnterpriseDTO dto) {
        EnterpriseDO enterpriseDO = enterpriseMapper.selectByPrimaryKey(dto);
        if (enterpriseDO == null) {
            enterpriseMapper.saveEnterprise(dto);
        }else {
            boolean isNeed = isNeedUpdate(enterpriseDO,dto);
            if(isNeed) {
                enterpriseMapper.updateEnterpriseByPrimaryKey(dto);
            }
        }

    }

    /**
     * 如果enterpriseDO的法人、电话、税务等级有一个不同则需要更新
     * @param enterpriseDO
     * @param dto
     * @return true:需要更新  false:不需要更新
     */
    private boolean isNeedUpdate(EnterpriseDO enterpriseDO, EnterpriseDTO dto) {
        return !Objects.equals(enterpriseDO.getLegalPerson(), dto.getLegalPerson())
                || !Objects.equals(enterpriseDO.getPhone(), dto.getPhone())
                || !Objects.equals(enterpriseDO.getTaxGrade(), dto.getTaxGrade());
    }
}
