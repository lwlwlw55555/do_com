package com.domdd.controller.open;

import com.domdd.controller.base.resp.BaseResp;
import com.domdd.enums.upload.UploadShopNameEnum;
import com.domdd.enums.upload.UploadTypeEnum;
import com.domdd.model.SelectVo;
import com.domdd.service.UploadService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "[upload]")
@RestController
@RequestMapping("upload")
@ApiImplicitParams({
        @ApiImplicitParam(name = "partyId", paramType = "header", type = "int", required = true, example = "1"),
        @ApiImplicitParam(name = "bizIds", paramType = "header", type = "String", example = "1")
})
@Controller
public class UploadController {
    @Resource
    private UploadService uploadService;

    @PostMapping("uploadType")
    @ApiOperation("上传类型")
    @ApiOperationSupport(author = "lw")
    public BaseResp<List<SelectVo>> uploadType() {
        return BaseResp.success(uploadService.uploadType());
    }

    @PostMapping("uploadShopNameType")
    @ApiOperation("上传店铺类型")
    @ApiOperationSupport(author = "lw")
    public BaseResp<List<SelectVo>> uploadShopNameType() {
        return BaseResp.success(uploadService.uploadShopNameType());
    }

    @PostMapping("upload")
    @ApiOperation("导入(废弃)")
    @ApiOperationSupport(author = "lw")
    public BaseResp<Integer> upload(MultipartFile file, UploadTypeEnum uploadType, UploadShopNameEnum uploadShopName) {
        return BaseResp.success(uploadService.upload(file, uploadType, uploadShopName));
    }

    @PostMapping("uploadFile")
    @ApiOperation("导入文件")
    @ApiOperationSupport(author = "lw")
    public BaseResp<Integer> upload(MultipartFile file) {
        return BaseResp.success(uploadService.upload(file, null, null));
    }

}
