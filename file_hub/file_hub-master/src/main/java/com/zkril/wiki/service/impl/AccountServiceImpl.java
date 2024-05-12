package com.zkril.wiki.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkril.wiki.pojo.Account;
import com.zkril.wiki.service.AccountService;
import com.zkril.wiki.mapper.AccountMapper;
import org.springframework.stereotype.Service;

/**
* @author zkril
* @description 针对表【account】的数据库操作Service实现
* @createDate 2024-04-05 16:06:07
*/
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account>
    implements AccountService{
}




