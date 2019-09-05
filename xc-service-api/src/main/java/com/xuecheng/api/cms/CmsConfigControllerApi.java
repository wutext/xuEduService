package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value="cms轮播图管理",description = "cms轮播图接口，提供页面的增、删、改、查")
public interface CmsConfigControllerApi {

    @ApiOperation("根据id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "ID",required=true,paramType="path",dataType="String"),
    })
    public CmsConfig findById(String id);
}
