package com.xuecheng.manage_course.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.netflix.discovery.converters.Auto;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_course.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseBaseRepository courseBaseRepository;
    @Autowired
    private CoursePicRepository coursePicRepository;
    @Autowired
    private CourseMarketRepository courseMarketRepository;
    @Autowired
    private TeachPlanRepository teachPlanRepository;
    @Autowired
    private TeachPlanMapper teachPlanMapper;

    public QueryResponseResult findPageCourseBase(int pageNo, int pageSize) {

        PageHelper.startPage(pageNo,pageSize);
        Page<CourseInfo> courseBasePage = courseMapper.findPageCourseBase();

        QueryResult<CourseInfo> courseInfoQueryResult = new QueryResult<>();
        courseInfoQueryResult.setList(courseBasePage.getResult());
        courseInfoQueryResult.setTotal(courseBasePage.getTotal());

        return new QueryResponseResult(CommonCode.SUCCESS, courseInfoQueryResult);
    }

    public CourseView getCourseView(String id) {
        CourseView courseView = new CourseView();
        //课程基本信息
        Optional<CourseBase> courseBase = courseBaseRepository.findById(id);
        if(courseBase.isPresent()) {
            courseView.setCourseBase(courseBase.get());
        }
        //课程图片信息
        Optional<CoursePic> coursePic = coursePicRepository.findById(id);
        if(coursePic.isPresent()) {
            courseView.setCoursePic(coursePic.get());
        }
        Optional<CourseMarket> courseMarket = courseMarketRepository.findById(id);
        if(courseMarket.isPresent()) {
            courseView.setCourseMarket(courseMarket.get());
        }
        TeachplanNode teachplanNode = teachPlanMapper.selectList(id);
        courseView.setTeachplanNode(teachplanNode);
        return courseView;
    }
}
