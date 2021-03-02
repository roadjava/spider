package com.threepeople.spider.bean.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 爬取请求通过此类构造入参
 */
@Data
public class CrawlRequest {
    /**
     * 要爬取的网址,对应一个企业
     */
    @NotBlank
    private String url;
    /**
     *企业名字css选择器
     */
    @NotBlank
    private String enterpriseNameSelector;
    /**
     * 法人css选择器
     */
    @NotBlank
    private String legalPersonSelector;
    /**
     * 企业电话css选择器
     */
    @NotBlank
    private String phoneSelector;
    /**
     * 税务评级css选择器
     */
    @NotBlank
    private String taxGradeSelector;
}
