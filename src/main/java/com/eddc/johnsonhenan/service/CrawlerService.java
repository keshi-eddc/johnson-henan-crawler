package com.eddc.johnsonhenan.service;

import java.util.Map;

public interface CrawlerService {
    //1.获得页面
    public String getPostContent(String url, Map<String, String> headParms);

    //2.解析页面,数据入库
    public void extraOnePageContent(String content, String account ,String isInsert);

}
