package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CourseControllerApi;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.CourseService;
import com.xuecheng.manage_course.service.TeachPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("course")
public class TeachPlanController implements CourseControllerApi {

    @Autowired
    private TeachPlanService teachPlanService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/teachplan/list/{courseId}")
    public TeachplanNode selectList(@PathVariable("courseId") String courseId) {
        return teachPlanService.selectList(courseId);
    }

    @PostMapping("/teachplan/add")
    public ResponseResult add(@RequestBody Teachplan teachplan) {
        return teachPlanService.add(teachplan);
    }

    @GetMapping("/coursebase/list/{page}/{size}")
    public QueryResponseResult findCourseList(@PathVariable("page") int pageNo,
                                              @PathVariable("size") int pageSize,
                                              CourseListRequest courseListRequest) {

        return courseService.findPageCourseBase(pageNo,pageSize);
    }

    @GetMapping("/courseView/{id}")
    public CourseView courseView(@PathVariable("id") String id) {
        return courseService.getCourseView(id);
    }
}
