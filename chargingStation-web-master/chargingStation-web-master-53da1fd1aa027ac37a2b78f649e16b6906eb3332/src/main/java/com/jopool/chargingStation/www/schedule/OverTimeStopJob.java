package com.jopool.chargingStation.www.schedule;

import com.alibaba.fastjson.JSON;
import com.jopool.chargingStation.www.mqtt.constants.ReportTypeEnum;
import com.jopool.chargingStation.www.service.OrderService;
import com.jopool.jweb.quartz.QuartzSchedulerService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package Name : com.jopool.chargingStation.www.schedule
 * @Author : soupcat
 * @Creation Date : 2017/9/7 下午4:20
 * <p>异常拔出插头，设备断电，订单暂停一段时间后没有插入插头则关闭订单
 */
public class OverTimeStopJob implements Job {
    private static Logger logger = LoggerFactory.getLogger(OverTimeStopJob.class);

    @Resource
    private OrderService orderService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Object extra = context.getJobDetail().getJobDataMap().get(QuartzSchedulerService.EXTRA_JSON);
        Map<String, String> dataMap = JSON.parseObject(extra.toString(), HashMap.class);
        logger.debug("job executed,{}", extra);
        orderService.overTimeStop(dataMap.get("portId"), dataMap.get("orderId"));
    }
}
