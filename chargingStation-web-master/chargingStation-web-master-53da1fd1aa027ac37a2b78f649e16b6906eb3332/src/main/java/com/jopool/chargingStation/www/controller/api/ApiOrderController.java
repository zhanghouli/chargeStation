package com.jopool.chargingStation.www.controller.api;

import com.jopool.chargingStation.www.controller.BaseController;
import com.jopool.chargingStation.www.response.OrderListResp;
import com.jopool.chargingStation.www.service.OrderService;
import com.jopool.jweb.entity.BaseParam;
import com.jopool.jweb.entity.PageResult;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.mybatis.page.Pagination;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.controller.api
 * @Author : soupcat
 * @Creation Date : 2017年08月25日 下午2:38
 */
@RestController
@RequestMapping("/api/order")
public class ApiOrderController extends BaseController {
    @Resource
    private OrderService orderService;

    /**
     * HYD0101充电记录列表
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864564
     *
     * @param baseParam
     * @return
     */
    @RequestMapping("getOrders.htm")
    public Result getOrders(BaseParam baseParam, String stationId, String status, Pagination page) {
        List<OrderListResp> resps = orderService.getOrders(baseParam.getPassportId(), stationId, status, page.page());
        return new PageResult(resps, page.getPaginator());
    }

    /**
     * HYD0102充电记录详情
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864566
     *
     * @param orderId
     * @return
     */
    @RequestMapping("getOrderDetail.htm")
    public Result getOrderDetail(String orderId) {
        validateParam(orderId);
        OrderListResp resp = orderService.getOrderDetail(orderId);
        return new Result(Code.SUCCESS, resp);
    }
}
