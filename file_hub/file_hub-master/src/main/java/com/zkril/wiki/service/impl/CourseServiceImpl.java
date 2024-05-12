package com.zkril.wiki.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkril.wiki.pojo.Course;
import com.zkril.wiki.service.CourseService;
import com.zkril.wiki.mapper.CourseMapper;
import org.springframework.stereotype.Service;

/**
* @author zkril
* @description 针对表【course】的数据库操作Service实现
* @createDate 2024-04-05 16:06:29
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService{

}




