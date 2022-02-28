//package com.domdd.controller;
//
//import com.domdd.controller.base.resp.BaseResp;
//import com.domdd.model.es.Applog;
//import com.domdd.model.es.User;
//import com.domdd.service.es.ApplogService;
//import com.domdd.service.lw.LwService;
//import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.AllArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * @author lw
// * @date 2022/2/16 4:37 下午
// */
//@Api(tags = "[applog]")
//@RestController
//@RequestMapping("applog")
//@AllArgsConstructor
//public class ApplogController {
//    private final ApplogService applogService;
//    private final LwService lwService;
//
//    @GetMapping("getAllDataByPage")
//    @ApiOperation("分页查询所有数据")
//    @ApiOperationSupport(author = "lw")
//    public BaseResp getAllDataByPage() {
//        //本该传入page和size，这里为了方便就直接写死了
//        Pageable page = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
//        Page<Applog> all = applogService.findAll(page);
//        return BaseResp.success(all.getContent());
//    }
//
//    @GetMapping("getDataByName")
//    @ApiOperation("根据By查询")
//    @ApiOperationSupport(author = "lw")
//    public BaseResp getDataByName(String name) {
//        List<Applog> lw = applogService.findApplogBy(name);
//        return BaseResp.success(lw);
//    }
//
//    @GetMapping("getDataByMessage")
//    @ApiOperation("根据log查询")
//    @ApiOperationSupport(author = "lw")
//    public BaseResp getDataByMessage(String info) {
//        List<Applog> res = applogService.findApplogByMessage(info);
//        return BaseResp.success(res);
//    }
//}
