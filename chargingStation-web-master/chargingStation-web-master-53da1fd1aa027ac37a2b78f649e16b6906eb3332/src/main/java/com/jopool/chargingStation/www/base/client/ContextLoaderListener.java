/* 
 * @(#)ContextLoaderListener.java    Created on 2013年9月12日
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.jopool.chargingStation.www.base.client;

import com.jopool.chargingStation.www.base.entity.Station;
import com.jopool.chargingStation.www.base.helper.ApplicationConfigHelper;
import com.jopool.chargingStation.www.service.StationService;
import com.jopool.jweb.enums.ModeEnum;
import com.jopool.jweb.mybatis.page.Pagination;
import com.jopool.jweb.utils.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author huangwq
 * @version $Revision: 1.0 $, $Date: 2013年9月12日 下午9:28:19 $
 */
public class ContextLoaderListener implements ServletContextListener {
    private HttpSolrClient httpSolrClient;
    private StationService stationService;

    @Override
    public void contextInitialized(ServletContextEvent c) {
        if (ApplicationConfigHelper.getMode() != ModeEnum.RELEASE) {
            return;
        }
        //get bean
        WebApplicationContext appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(c
                .getServletContext());
        httpSolrClient = (HttpSolrClient) appContext.getBean("httpSolrClient");
        stationService = (StationService) appContext.getBean("stationServiceImpl");
        //
        List<Station> stations = stationService.getAllStation();
        Collection<SolrInputDocument> docList = new ArrayList<SolrInputDocument>();
        for (Station station : stations) {
            //添加基站数据到Solr索引中
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("station_id", station.getId());
            if (!StringUtils.isEmpty(station.getAreaDes()) && !StringUtils.isEmpty(station.getAddress())) {
                doc.addField("station_address", station.getAreaDes() + "," + station.getAddress());
            }
            if (!StringUtils.isEmpty(station.getArea())) {
                doc.addField("station_area", station.getArea());
            }
            if (!StringUtils.isEmpty(station.getName())) {
                doc.addField("station_name", station.getName());
            }
            String posString = station.getLngE5() / 1E5 + " " + station.getLatE5() / 1E5;
            doc.addField("station_position", posString);
            docList.add(doc);
        }
        try {
            httpSolrClient.add(docList);
            httpSolrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Index base station data done!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent c) {
        AliyunOSSClient.getInstance().shutdown();
    }
}
