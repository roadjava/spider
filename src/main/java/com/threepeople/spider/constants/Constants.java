package com.threepeople.spider.constants;

import java.nio.charset.StandardCharsets;

/**
 * 常量类
 */
public class Constants {
    public static final String UTF_8 = StandardCharsets.UTF_8.name();
    /**
     * 天眼查的CSS选择器
     */
    public static final String TYC_NAME_SELECTOR = "#nav-main-stockNum > div.data-content > table > tbody > tr:nth-child(1) > td:nth-child(2)";
    public static final String TYC_LEGAL_PERSON_SELECTOR = "#nav-main-stockNum > div.data-content > table > tbody > tr:nth-child(5) > td:nth-child(2) > a";
    public static final String TYC_PHONE_SELECTOR = "#_container_corpContactInfo > table > tbody > tr:nth-child(1) > td:nth-child(2)";
    public static final String TYC_TAX_GRADE_SELECTOR = "#_container_taxcredit > table > tbody > tr:nth-child(1)";

}
