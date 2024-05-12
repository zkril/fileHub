package com.zkril.wiki.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName course
 */
@TableName(value ="course")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Object courseid;

    /**
     * 课程名称
     */
    private String courseName;


    /**
     * 关联账号id
     */
    private Integer accountid;


}