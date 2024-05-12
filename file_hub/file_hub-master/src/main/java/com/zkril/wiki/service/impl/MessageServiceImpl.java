package com.zkril.wiki.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkril.wiki.pojo.Message;
import com.zkril.wiki.service.MessageService;
import com.zkril.wiki.mapper.MessageMapper;
import org.springframework.stereotype.Service;

/**
* @author zkril
* @description 针对表【message】的数据库操作Service实现
* @createDate 2024-04-02 19:31:58
*/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
    implements MessageService{

}




