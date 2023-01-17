package com.domdd.controller.history;

import com.domdd.controller.base.resp.BaseResp;
import com.domdd.controller.req.HistoryBaseReq;
import com.domdd.service.OpenService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(tags = "[history]")
@RestController
@RequestMapping("history")
@ApiImplicitParams({
        @ApiImplicitParam(name = "partyId", paramType = "header", type = "int", required = true, example = "1"),
        @ApiImplicitParam(name = "bizIds", paramType = "header", type = "String", example = "1")
})
@Controller
public class HistoryController {
    @Resource
    private OpenService openService;

    @PostMapping("purchaseInOrder/list")
    @ApiOperation("采购入库单刷新")
    @ApiOperationSupport(author = "lw")
    public BaseResp purchaseInOrderList(@RequestBody @Valid HistoryBaseReq req, BindingResult bindingResult) {
        openService.startRefreshPurchaseInOrder(req.getStartDate(), req.getEndDate());
        return BaseResp.success();
    }

    @PostMapping("order/list")
    @ApiOperation("销售订单刷新")
    @ApiOperationSupport(author = "lw")
    public BaseResp orderList(@RequestBody @Valid HistoryBaseReq req, BindingResult bindingResult) {
        openService.startRefreshOrderList(req.getStartDate(), req.getEndDate());
        return BaseResp.success();
    }

    @PostMapping("afterSaleOrder/list")
    @ApiOperation("退款单刷新")
    @ApiOperationSupport(author = "lw")
    public BaseResp afterSaleOrderList(@RequestBody @Valid HistoryBaseReq req, BindingResult bindingResult) {
        openService.startRefreshAfterSaleList(req.getStartDate(), req.getEndDate());
        return BaseResp.success();
    }

    @PostMapping("afterSaleReturnOrder/list")
    @ApiOperation("退货单刷新")
    @ApiOperationSupport(author = "lw")
    public BaseResp afterSaleReturnOrderList(@RequestBody @Valid HistoryBaseReq req, BindingResult bindingResult) {
        openService.startRefreshAfterSaleRefundList(req.getStartDate(), req.getEndDate());
        return BaseResp.success();
    }

    @PostMapping("inventory/list")
    @ApiOperation("库存表刷新")
    @ApiOperationSupport(author = "lw")
    public BaseResp inventoryList(@RequestBody @Valid HistoryBaseReq req, BindingResult bindingResult) {
        openService.startRefreshInventoryList(req.getStartDate(), req.getEndDate());
        return BaseResp.success();
    }

}
