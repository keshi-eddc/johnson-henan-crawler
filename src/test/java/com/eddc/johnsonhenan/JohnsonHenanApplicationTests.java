package com.eddc.johnsonhenan;

import com.eddc.johnsonhenan.service.CrawlerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JohnsonHenanApplicationTests {
    private static Logger logger = LogManager.getLogger(JohnsonHenanApplicationTests.class.getName());
    /*
     *r入口： http://hc.hnggzyjy.cn/UserLogin.aspx
     */
    @Autowired
    CrawlerService crawlerService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void getcontentTest() {
        Map<String, String> headParams = new HashMap<>();
//        String url = "https://www.hao123.com/";
        String url = "http://hc.hnggzyjy.cn/Enterprise/RelationQuery/RelationDistributionQuery.aspx";
//        params.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//        params.put("Accept-Encoding", "gzip, deflate");
//        params.put("Accept-Language", "zh-CN,zh;q=0.9");
//        params.put("Cache-Control", "max-age=0");
//        params.put("Connection", "keep-alive");
//        params.put("Content-Length", "0");
//        params.put("Content-Type", "application/x-www-form-urlencoded");
        headParams.put("Cookie", "9=100; 15=100; ASP.NET_SessionId=5kmtfouxkceoac55smsjqy45");
//        params.put("Host", "hc.hnggzyjy.cn");
//        params.put("Origin", "http://hc.hnggzyjy.cn");
//        params.put("Referer", "http://hc.hnggzyjy.cn/Default1.aspx?mid=1247&linkPage=Enterprise/RelationQuery/DistributionToCompany.aspx");
//        params.put("Upgrade-Insecure-Requests", "1");
//        params.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");

        Map<String, Object> formParams = new HashMap<>();
        crawlerService.getPostContent(url, headParams);
    }

    @Test
    public void extraTest() {
        //请求头
        Map<String, String> headParams = new HashMap<>();
//        String url = "http://hc.hnggzyjy.cn/Enterprise/RelationQuery/RelationDistributionQuery.aspx";
        String url = "http://hc.hnggzyjy.cn/Enterprise/RelationQuery/RelationDistributionQuery.aspx?returnUrl=%2fEnterprise%2fRelationQuery%2fRelationQueryUnpack.aspx";
//        String url = "http://hc.hnggzyjy.cn/Enterprise/RelationQuery/DistributionToCompanyMain.aspx?returnUrl=%2fEnterprise%2fRelationQuery%2fDistributionToCompany.aspx";
        // 9 = 每页显示数量 2018年11月19日
        // 6 = 每页显示数量 2018年11月20日
        headParams.put("Cookie", "15=10; 6=100; 9=100; ASP.NET_SessionId=knhd1a45h1455w554tb0qonv");
//        headParams.put("","");
//        headParams.put("Upgrade-Insecure-Requests", "1");
//        headParams.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
//        headParams.put("Connection","keep-alive");
//        headParams.put("Cache-Control","max-age=0");
//        headParams.put("","");
//        headParams.put("__EVENTTARGET", "ctl00%24ContentPlaceHolder1%24AspNetPager1");
//        headParams.put("__EVENTARGUMENT", "3");
//        headParams.put("ctl00%24ContentPlaceHolder1%24AspNetPager1_pagesize", "10");


        //翻页
//        url = url + "&__EVENTTARGET=ctl00%24ContentPlaceHolder1%24AspNetPager1" +
//                "&__EVENTARGUMENT=2" +
//                "&ctl00%24ContentPlaceHolder1%24AspNetPager1_pagesize=10";

//        url = "http://hc.hnggzyjy.cn/Enterprise/RelationQuery/RelationDistributionQuery.aspx?returnUrl=%2fEnterprise%2fRelationQuery%2fRelationQueryUnpack.aspx" +
//        url = url + "&__EVENTTARGET=ctl00%24ContentPlaceHolder1%24AspNetPager1" +
//                "&__EVENTARGUMENT=2" +
//                "&ctl00%24ContentPlaceHolder1%24AspNetPager1_pagesize=20";

        String content = crawlerService.getPostContent(url, headParams);
        crawlerService.extraOnePageContent(content, "hs0014","no");

        System.out.println("url:" + url);

    }

    /* 抓取时间，每月前5号
     * 抓取账号前，登录网页，获得cookie
     * 注意cookie里，显示每页数量的参数，确保数据完整
     * 9 = 每页显示数量 2018年11月19日
     * 6 = 每页显示数量 2018年11月20日
     * */
    @Test
    public void getDataWithAccount() {
        String account = "hs0014";
        String NET_SessionId = "fhymbrviotgqn0uc5wfkn3ex";

        //yes 才插入数据库
        String isInsert = "no";
        String url = "http://hc.hnggzyjy.cn/Enterprise/RelationQuery/RelationDistributionQuery.aspx?returnUrl=%2fEnterprise%2fRelationQuery%2fRelationQueryUnpack.aspx";
        Map<String, String> headParams = new HashMap<>();
        String cookieStr = "15=10; 6=1000; 9=1000; ASP.NET_SessionId=" + NET_SessionId;
        headParams.put("Cookie", cookieStr);
        String content = crawlerService.getPostContent(url, headParams);
        crawlerService.extraOnePageContent(content, account, isInsert);

    }


}
