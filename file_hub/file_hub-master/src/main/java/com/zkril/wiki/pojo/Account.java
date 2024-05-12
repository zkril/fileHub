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
 * @TableName account
 */
@TableName(value ="account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Object countid;


    /**
     * 账号名称
     */
    private String uname;

    /**
     * 账号密码
     */
    private String accountPassword;


}