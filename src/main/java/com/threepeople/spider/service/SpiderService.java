package com.threepeople.spider.service;

import com.threepeople.spider.bean.dto.EnterpriseDTO;
import com.threepeople.spider.bean.req.CrawlRequest;

import java.util.List;

/**
 * 实现爬虫功能
 */
public interface SpiderService {
    /**
     * 通过组装筛选条件获取需要爬取的企业对应的url,
     * 并将url以及css选择器组装成CrawlRequest对象返回
     * @return
     */
    List<CrawlRequest> acquireRequests();
    /**
     * 爬取一个url并组装成企业对象
     * @param request 入参
     * @return 企业对象dto
     */
    EnterpriseDTO crawl(CrawlRequest request);
}
