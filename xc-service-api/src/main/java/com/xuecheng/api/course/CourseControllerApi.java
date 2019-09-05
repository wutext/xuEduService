package com.xuecheng.api.course;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value="课程管理页面接口API",description = "课程管理页面接口，提供页面的增、删、改、查")
public interface CourseControllerApi {
    //页面查询
    @ApiOperation("查询页面列表")
    @ApiImplicitParam(name = "courseId", value = "课程ID", required = true, paramType = "path", dataType = "String")
    TeachplanNode selectList(String courseId);

    //页面查询
    @ApiOperation("添加课程计划")
    ResponseResult add(Teachplan teachplan);

    @ApiOperation("分页查询课程")
    public QueryResponseResult findCourseList(int pageNo, int pageSize,
                                                   CourseListRequest courseListRequest);

    @ApiOperation("试图课程查询")
    public CourseView courseView(String id);
}
