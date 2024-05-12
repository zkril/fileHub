package com.zkril.wiki.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zkril.wiki.mapper.AccountMapper;
import com.zkril.wiki.mapper.CourseMapper;
import com.zkril.wiki.pojo.Account;
import com.zkril.wiki.pojo.Course;
import com.zkril.wiki.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseMapper courseMapper;

    @GetMapping("/search")
    public Result getPersonCourseList(String countid) {
        System.out.println(countid);
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("accountid", countid);
        List<Course> courseList = courseMapper.selectList(courseQueryWrapper);
        return Result.ok(courseList);
    }
    @PostMapping(value="change")
    public Result changeCourse(@RequestBody Course course) {
        if(courseMapper.updateById(course)!=0)
            return Result.ok();
        else
            return Result.fail();
    }


    @PostMapping("/add")
    public Result addCourse(String courseName,String accountId) {
        System.out.println(courseName);
        System.out.println(accountId);
        if (courseName == null|| courseName.isEmpty()) {
            return Result.fail("缺少必要的字段");
        }

        Course course = new Course();
        course.setAccountid(Integer.valueOf(accountId));
        course.setCourseName(courseName);

        if (courseMapper.insert(course)>0) {
            return Result.ok("新建课程成功");
        } else {
            return Result.fail("新建课程失败");
        }
    }




    @DeleteMapping("/delete")
    public Result deleteCourse(String courseId) {
        System.out.println(courseId);
        if (courseId == null) {
            return Result.fail("缺少课程id");
        }

        if (courseMapper.deleteById(courseId)!=0) {
            return Result.ok("删除课程成功");
        } else {
            return Result.fail("删除课程失败");
        }
    }
}
