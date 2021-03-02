package com.threepeople.spider.bean.dto;

import lombok.Data;

/**
 * 爬虫组装结果
 */
@Data
public class EnterpriseDTO {
    /**
     * 企业名字
     */
    private String enterpriseName;
    /**
     * 法人
     */
    private String legalPerson;
    /**
     * 企业电话
     */
    private String phone;
    /**
     * 税务评级
     */
    private String taxGrade;
}
