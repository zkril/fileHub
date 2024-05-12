package com.zkril.wiki.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @TableName message
 */
@TableName(value ="message")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Object messageid;

    /**
     * 平台显示信息id
     */
    private String mid;

    /**
     * 信息标题
     */
    private String messageName;

    /**
     * 信息主体
     */
    private String messageBody;

    /**
     * 关联账号id
     */
    private Integer accountid;

    /**
     * 关联课程id
     */
    private Integer courseid;

    /**
     * 截止时间
     */
    private String ddl;


}