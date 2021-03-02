package com.threepeople.spider;

import com.threepeople.spider.bean.dto.EnterpriseDTO;
import com.threepeople.spider.bean.req.CrawlRequest;
import com.threepeople.spider.constants.Constants;
import com.threepeople.spider.service.SpiderService;
import com.threepeople.spider.service.impl.SpiderServiceImpl;
import com.threepeople.spider.task.SpiderTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author zhaodaowen
 * @see <a href="http://www.roadjava.com">乐之者java</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpiderTest {
    @Resource
    private SpiderTask spiderTask;
    @Test
    public void test1(){
        spiderTask.scheduleExec();
    }
}
