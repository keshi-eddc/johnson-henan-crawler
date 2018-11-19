package com.eddc.johnsonhenan.service.impl;

import com.eddc.johnsonhenan.service.CrawlerService;
import com.eddc.johnsonhenan.utils.http.HttpClientUtil;
import com.eddc.johnsonhenan.utils.http.request.MultiPartFormRequest;
import com.eddc.johnsonhenan.utils.http.request.RequestMethod;
import com.eddc.johnsonhenan.utils.http.response.Response;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.awt.peer.ChoicePeer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(value = "CrawlerService")
public class CrawlerServiceImpl implements CrawlerService {
    Logger logger = LogManager.getLogger(CrawlerServiceImpl.class.getName());

    @Override
    public String getPostContent(String url, Map<String, String> headParms, Map<String, Object> formParms) {
        logger.info("- 获得页面");
        String content = "";
        if (StringUtils.isNotEmpty(url)) {
            try {
                MultiPartFormRequest request = new MultiPartFormRequest(url, RequestMethod.POST);
                request.addHeaders(headParms);
//                request.addParts(formParms);
                Response response = HttpClientUtil.doRequest(request);

                int code = response.getCode();
                if (code == 200) {
                    content = response.getResponseText();
                    if (StringUtils.isNotBlank(content)) {
                        logger.info("- 页面正常");
                    } else {
                        logger.error("返回空");
                    }
                } else {
                    logger.error("请求错误：" + code);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return content;
    }


    @Override
    public void extraOnePageContent(String content) {
        logger.info("- 解析页面");
        //检查页面是否为空
        if (StringUtils.isNotEmpty(content)) {
            //检查页面是否正常
            if (content.contains("mainlist")) {
                try {
                    Document doc = Jsoup.parse(content);
                    logger.info("网页标题：" + doc.title());
                    //页码
                    Elements pageGuideseles = doc.select("div#ctl00_ContentPlaceHolder1_AspNetPager1 > div:nth-child(1)");
                    String pageGuideStr = "";
                    if (pageGuideseles != null && pageGuideseles.size() > 0) {
                        pageGuideStr = pageGuideseles.text();
                    }

                    Elements lineseles = doc.select("div> table.mainlist > tbody > tr");
                    if (lineseles != null && lineseles.size() > 0) {
                        logger.info("- 表有：" + lineseles.size() + " 行");
                        //表头
                        Elements headLineles = lineseles.get(0).select("th");
                        if (headLineles != null && headLineles.size() > 0) {
                            for (Element headele : headLineles) {
                                logger.info("表头：" + headele.text());
                            }
                        }
                        //表内容
                        for (int i = 1; i < lineseles.size(); i++) {
                            logger.info("============== " + i);
                            Element linele = lineseles.get(i);
                            Elements lineTDseles = linele.select("td");
                            if (lineTDseles != null && lineTDseles.size() > 0) {
                                for (Element lineTDsele : lineTDseles) {
                                    logger.info(lineTDsele.text());
                                }
                            }
                        }

                        logger.info("页码导航：" + pageGuideStr);

                    } else {
                        logger.info("表有0行");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("！！！解析异常");
                }
            } else {
                logger.error("！！！异常页面");
            }
        } else {
            logger.error("！！！空页面，不解析");
        }

    }
}
