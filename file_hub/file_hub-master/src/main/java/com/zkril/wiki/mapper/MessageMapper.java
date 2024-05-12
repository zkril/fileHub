package com.zkril.wiki.mapper;

import com.zkril.wiki.pojo.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author zkril
* @description 针对表【message】的数据库操作Mapper
* @createDate 2024-04-02 19:31:58
* @Entity com.zkril.wiki.pojo.Message
*/
@Repository
public interface MessageMapper extends BaseMapper<Message> {

}




