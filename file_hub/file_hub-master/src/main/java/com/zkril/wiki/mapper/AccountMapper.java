package com.zkril.wiki.mapper;

import com.zkril.wiki.pojo.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author zkril
* @description 针对表【account】的数据库操作Mapper
* @createDate 2024-04-05 16:06:07
* @Entity com.zkril.wiki.pojo.Account
*/
@Repository
public interface AccountMapper extends BaseMapper<Account> {

}




