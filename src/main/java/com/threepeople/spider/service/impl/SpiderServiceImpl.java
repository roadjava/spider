package com.threepeople.spider.service.impl;

import com.threepeople.spider.bean.dto.EnterpriseDTO;
import com.threepeople.spider.bean.req.CrawlRequest;
import com.threepeople.spider.constants.Constants;
import com.threepeople.spider.service.SpiderService;
import com.threepeople.spider.util.HttpClientUtil;
import com.threepeople.spider.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
@Slf4j
public class SpiderServiceImpl implements SpiderService {
    @Override
    public EnterpriseDTO crawl(CrawlRequest request) {
        // 参数校验
        boolean validResult = ValidationUtil.valid(request);
        if (!validResult) {
            log.error("入参校验不通过,入参为:{}",request);
            throw new RuntimeException("入参校验不通过");
        }
        // 请求url并获取返回的html字符串
        String htmlStr = executeUrl(request.getUrl());
        if (StringUtils.isBlank(htmlStr)) {
            return null;
        }
        // 根据html字符串解析并构造EnterpriseDTO结果
        EnterpriseDTO dto = buildEnterpriseDTO(request,htmlStr);
        return dto;
    }

    @Override
    public List<CrawlRequest> acquireRequests() {
        // 获取根据一定的筛选条件筛选出企业的url
        List<CrawlRequest> list = new ArrayList<>();

        CrawlRequest crawlRequest = new CrawlRequest();
        crawlRequest.setUrl("https://www.tianyancha.com/company/61407195");
        crawlRequest.setEnterpriseNameSelector(Constants.TYC_NAME_SELECTOR);
        crawlRequest.setLegalPersonSelector(Constants.TYC_LEGAL_PERSON_SELECTOR);
        crawlRequest.setPhoneSelector(Constants.TYC_PHONE_SELECTOR);
        crawlRequest.setTaxGradeSelector(Constants.TYC_TAX_GRADE_SELECTOR);

        list.add(crawlRequest);
        return list;
    }

    private EnterpriseDTO buildEnterpriseDTO(CrawlRequest request, String htmlStr) {
        Document document = Jsoup.parse(htmlStr,request.getUrl());
        // 获取税务等级
        String taxGradeSelector = request.getTaxGradeSelector();
        Elements taxGradeElements = document.select(taxGradeSelector);
        if (taxGradeElements.isEmpty()) {
            log.info("税务等级为空,略过");
            return null;
        }
        EnterpriseDTO dto = new EnterpriseDTO();
        String	taxGrade = taxGradeElements.get(0).text();
        dto.setTaxGrade(taxGrade);
        // 获取企业名字
        Elements nameElements = document.select(request.getEnterpriseNameSelector());
        if (nameElements.isEmpty()) {
            log.info("企业名字为空,略过");
            return null;
        }
        String name = nameElements.get(0).text();
        dto.setEnterpriseName(name);
        // 获取法人
        Elements legalPersonElements = document.select(request.getLegalPersonSelector());
        if (legalPersonElements.size() > 0) {
            dto.setLegalPerson(legalPersonElements.get(0).text());
        }
        // 获取企业电话
        Elements phoneElements = document.select(request.getPhoneSelector());
        if (phoneElements.size() > 0) {
            dto.setPhone(phoneElements.get(0).text());
        }
        return dto;
    }

    /**
     * 发送http[s]请求并获取url对应的html字符串
     * @param url
     * @return
     */
    private String executeUrl(String url) {
        String htmlStr = HttpClientUtil.executeGet(url, Constants.UTF_8);
        if (StringUtils.isNoneBlank(htmlStr)) {
            String webSiteCharset = getUrlCharset(htmlStr);
            if (webSiteCharset!=null) {
                if (!webSiteCharset.equals(Constants.UTF_8)) {
                    htmlStr = HttpClientUtil.executeGet(url, webSiteCharset);
                }
            }
        }
        return htmlStr;
    }

    /**
     *得到html字符串中使用的编码:
     * h5:
     * <meta charset="utf-8">
     * html:
     * <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
     */
    private String getUrlCharset(String websiteHtml){
        String retCharset = null;
        Document document = Jsoup.parse(websiteHtml);
        Elements elements = document.select("meta[charset]");
        int size = elements.size();
        if (size==1) {//html5
            Element element = elements.get(0);//只应该有一个
            String attrVal = element.attr("charset");
            if (StringUtils.isNotBlank(attrVal)) {
                retCharset = attrVal.trim().toLowerCase();
            }
        }else{//html
            elements = document.select("meta[content]");
            size = elements.size();
            if (size>0) {//关键字 网站描述导致>0
                for (int i = 0; i <size; i++) {
                    Element element = elements.get(i);
                    String content = element.attr("content");
                    if (content.toLowerCase().contains("charset")) {
                        String[] split = content.split("=");
                        String val = split[1];
                        if (StringUtils.isNotBlank(val)) {
                            retCharset = val.trim().toLowerCase();
                        }
                        break;
                    }
                }
            }//else:使用默认utf-8编码
        }
        return retCharset;
    }
}
