package com.threepeople.spider.task;

import com.threepeople.spider.bean.dto.EnterpriseDTO;
import com.threepeople.spider.bean.req.CrawlRequest;
import com.threepeople.spider.service.EnterpriseService;
import com.threepeople.spider.service.SpiderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时执行爬虫任务
 */
@Component
public class SpiderTask {
    @Resource
    private SpiderService spiderService;
    @Resource
    private EnterpriseService enterpriseService;
    /**
     * 每天凌晨12点执行一次
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void scheduleExec() {
        List<CrawlRequest> crawlRequests = spiderService.acquireRequests();
        for (CrawlRequest crawlRequest : crawlRequests) {
            EnterpriseDTO enterpriseDTO = spiderService.crawl(crawlRequest);
            enterpriseService.process(enterpriseDTO);
        }
    }
}
