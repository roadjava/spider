package com.threepeople.spider.bean.entity;

import lombok.Data;

/**
 * 企业实体
 */
@Data
public class EnterpriseDO {
    /**
     * 数据库主键
     */
    private Long id;
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
    /**
     * 记录创建时间
     */
    private String createTime;
    /**
     * 记录最后修改时间
     */
    private String modifyTime;
}
