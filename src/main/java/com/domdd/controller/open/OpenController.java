package com.domdd.controller.open;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.domdd.controller.base.BaseController;
import com.domdd.controller.base.resp.BasePagingResp;
import com.domdd.controller.base.resp.BaseResp;
import com.domdd.controller.req.OpenInventoryListReq;
import com.domdd.controller.req.OpenOrderListReq;
import com.domdd.controller.req.OpenOrderShopReq;
import com.domdd.controller.req.OpenTimeReq;
import com.domdd.model.*;
import com.domdd.service.OpenService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "[open]")
@RestController
@RequestMapping("open")
@ApiImplicitParams({
        @ApiImplicitParam(name = "partyId", paramType = "header", type = "int", required = true, example = "1"),
        @ApiImplicitParam(name = "bizIds", paramType = "header", type = "String", example = "1")
})
@Controller
@Slf4j
public class OpenController extends BaseController {

    @Resource
    private OpenService openService;

    @PostMapping("purchaseInOrder/list")
    @ApiOperation("采购入库单查询")
    @ApiOperationSupport(author = "lw")
    public BaseResp<BasePagingResp<PurchaseInOrder>> purchaseInOrderList(@RequestBody @Valid OpenTimeReq req, BindingResult bindingResult) {
        log.info("[purchaseInOrder/list] params:{}", JSON.toJSONString(req));
        return BaseResp.success(new BasePagingResp<>(openService.purchaseInOrderList(req.getStartTime(), req.getEndTime(), req.getPage(), req.getPageSize())));
    }

    @PostMapping("order/list")
    @ApiOperation("销售订单查询")
    @ApiOperationSupport(author = "lw")
    public BaseResp<BasePagingResp<OrderInfo>> orderList(@RequestBody @Valid OpenOrderListReq req, BindingResult bindingResult) {
        log.info("[order/list] params:{}", JSON.toJSONString(req));
        IPage<OrderInfo> orderInfoPage = openService.orderList(req.getShopName(), req.getStartTime(), req.getEndTime(), req.getTimeType(), req.getPage(), req.getPageSize(), req.getOrderType());
        List<OrderInfo> records = orderInfoPage.getRecords();
        OrderInfo.checkParams(records);
        return BaseResp.success(new BasePagingResp<>(orderInfoPage));
    }

    @PostMapping("afterSaleOrder/list")
    @ApiOperation("退款单查询")
    @ApiOperationSupport(author = "lw")
    public BaseResp<BasePagingResp<AfterSaleOrder>> afterSaleOrderList(@RequestBody @Valid OpenOrderShopReq req, BindingResult bindingResult) {
        log.info("[afterSaleOrder/list] params:{}", JSON.toJSONString(req));
        return BaseResp.success(new BasePagingResp<>(openService.afterSaleOrderList(req.getShopName(), req.getStartTime(), req.getEndTime(), req.getPage(), req.getPageSize())));
    }

    @PostMapping("afterSaleReturnOrder/list")
    @ApiOperation("退货单查询")
    @ApiOperationSupport(author = "lw")
    public BaseResp<BasePagingResp<AfterSaleReturnOrder>> afterSaleReturnOrderList(@RequestBody @Valid OpenOrderShopReq req, BindingResult bindingResult) {
        log.info("[afterSaleReturnOrder/list] params:{}", JSON.toJSONString(req));
        return BaseResp.success(new BasePagingResp<>(openService.afterSaleReturnOrderList(req.getShopName(), req.getStartTime(), req.getEndTime(), req.getPage(), req.getPageSize())));
    }

    @PostMapping("inventory/list")
    @ApiOperation("库存表查询")
    @ApiOperationSupport(author = "lw")
    public BaseResp<BasePagingResp<Inventory>> inventoryList(@RequestBody @Valid OpenInventoryListReq req, BindingResult bindingResult) {
        log.info("[inventory/list] params:{}", JSON.toJSONString(req));
        return BaseResp.success(new BasePagingResp<>(openService.inventoryList(req.getPage(), req.getPageSize())));
    }
}
