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
 * @TableName messagefile
 */
@TableName(value ="messagefile")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Messagefile implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer fileid;

    /**
     * 
     */
    private Integer messageid;

    /**
     * 
     */
    private String oldname;

    /**
     * 
     */
    private String newname;

    /**
     * 
     */
    private Integer filesize;

    /**
     * 
     */
    private String path;


}