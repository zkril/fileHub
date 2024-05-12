package com.zkril.wiki.mapper;

import com.zkril.wiki.pojo.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author zkril
* @description 针对表【course】的数据库操作Mapper
* @createDate 2024-04-05 16:06:29
* @Entity com.zkril.wiki.pojo.Course
*/
@Repository
public interface CourseMapper extends BaseMapper<Course> {

}




