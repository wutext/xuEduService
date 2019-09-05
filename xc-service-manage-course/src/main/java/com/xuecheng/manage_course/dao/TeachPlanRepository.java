package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator.
 */
@Repository
public interface TeachPlanRepository extends JpaRepository<Teachplan,String> {

    List<Teachplan> findByCourseidAndParentid(String courseId, String parentId);
}
