package com.xuecheng.manage_course.service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.ext.TeachplanParameter;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseBaseRepository;
import com.xuecheng.manage_course.dao.TeachPlanMapper;
import com.xuecheng.manage_course.dao.TeachPlanRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
public class TeachPlanService {

    @Autowired
    private CourseBaseRepository courseBaseRepository;

    @Autowired
    private TeachPlanRepository teachPlanRepository;

    @Autowired
    private TeachPlanMapper teachPlanMapper;

    public TeachplanNode selectList(String courseId) {
        return teachPlanMapper.selectList(courseId);
    }

    @Transactional
    public ResponseResult add(Teachplan teachplan) {
        if(ObjectUtils.isEmpty(teachplan)
                || StringUtils.isEmpty(teachplan.getCourseid())
                || StringUtils.isEmpty(teachplan.getPname())) {

            ExceptionCast.cast(CommonCode.INVALID_PAAMS);
        }
        String courseId= teachplan.getCourseid();
        String parentId = teachplan.getParentid();
        if(StringUtils.isEmpty(parentId)) {
            parentId = getTemplateRoot(courseId);
        }
        Optional<Teachplan> teachplan1 = teachPlanRepository.findById(parentId);

        Teachplan newTeachPlan = new Teachplan();
        BeanUtils.copyProperties(teachplan,newTeachPlan);
        newTeachPlan.setCourseid(courseId);
        newTeachPlan.setParentid(parentId);
        Integer grade = Integer.valueOf(teachplan1.get().getGrade())+1;
        newTeachPlan.setGrade(String.valueOf(grade));

        teachPlanRepository.save(newTeachPlan);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    private String getTemplateRoot(String courseId) {

       Optional<CourseBase> courseBase = courseBaseRepository.findById(courseId);
       if(!courseBase.isPresent()) {
           return null;
       }
       List<Teachplan> teachplanList =teachPlanRepository.findByCourseidAndParentid(courseId, "0");

       if(CollectionUtils.isEmpty(teachplanList)) {
           Teachplan teachplan = new Teachplan();
           teachplan.setParentid("0");
           teachplan.setGrade("1");
           teachplan.setPname(courseBase.get().getName());
           teachplan.setStatus("0");
           teachplan.setCourseid(courseId);
           teachPlanRepository.save(teachplan);
           return teachplan.getId();
       }
       return teachplanList.get(0).getId();
    }
}
