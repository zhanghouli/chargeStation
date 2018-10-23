package com.jopool.chargingStation.www.controller.web;


import com.jopool.chargingStation.www.base.entity.*;
import com.jopool.chargingStation.www.controller.BaseController;
import com.jopool.chargingStation.www.dao.CarownerMapper;
import com.jopool.chargingStation.www.enums.OrderStatusEnum;
import com.jopool.chargingStation.www.service.OrderService;
import com.jopool.chargingStation.www.service.PassportService;
import com.jopool.chargingStation.www.service.StationService;
import com.jopool.chargingStation.www.vo.OrderListVo;
import com.jopool.chargingStation.www.vo.common.DateParam;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.mybatis.page.Pagination;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * @Package Name : com.jopool.chargingStation.www.controller.web
 * @Author : soupcat
 * @Creation Date : 2017/11/14 上午10:07
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {
    @Resource
    private OrderService    orderService;
    @Resource
    private PassportService passportService;
    @Resource
    private CarownerMapper  carownerMapper;
    @Resource
    private StationService  stationService;


    /**
     * 订单列表
     *
     * @param keyword
     * @param status
     * @param timeStartStr
     * @param timeEndStr
     * @param page
     * @return
     */
    @RequestMapping("orderList.htm")
    public ModelAndView orderList(String keyword, String status, String timeStartStr, String timeEndStr, Pagination page) throws ParseException {
        DateParam dateParam = new DateParam(timeStartStr, timeEndStr);
        // List<OrderListResp> resps = orderService.orderList(keyword, status,dateParam, page.page());
        List<OrderListVo> orderListVos = orderService.searchOrderListVo(keyword, status, dateParam, page.page());
        ModelAndView mv = getPageMv("order/orderList", orderListVos, page);
        return mv.addObject("status", status)
                .addObject("keyword", keyword)
                .addObject("timeStartStr", timeStartStr)
                .addObject("timeEndStr", timeEndStr);
    }

    /**
     * 订单详情
     *
     * @param carOwnerId
     * @param stationPortId
     * @param orderId
     * @return
     */
    @RequestMapping("orderListDetails.htm")
    public ModelAndView orderListDetails(String carOwnerId, String stationPortId, String orderId) {
        Carowner carowner = carownerMapper.selectByPrimaryKey(carOwnerId);
        Passport passport = new Passport();
        if (carowner != null) {
            passport = passportService.getById(carowner.getPassportId());
        }
        StationPort stationPort = new StationPort();
        Station station = new Station();
        stationPort = stationService.getPortById(stationPortId);
        if (stationPort != null) {
            station = stationService.getById(stationPort.getStationId());
        }
        Order order = orderService.getByOrderId(orderId);
        List<StationPortRealTimeListen> stationPortRealTimeListenList = stationService.getOrderStationPortDetails(stationPortId);
        ModelAndView modelAndView = new ModelAndView("order/orderListStationPort");
        return modelAndView
                .addObject("passport", passport)
                .addObject("stationPort", stationPort)
                .addObject("station", station)
                .addObject("order", order)
                .addObject("statusName", OrderStatusEnum.getVualeof(order.getStatus()).getName())
                .addObject("stationPortRealTimeListenList", stationPortRealTimeListenList);
    }

    /**
     * 订单删除
     *
     * @param orderId
     * @return
     */
    @RequestMapping("doRemoveOrder.htm")
    public Result doRemoveOrder(String orderId) {
        validateParam(orderId);
        return orderService.removeOrderId(orderId);
    }
}
