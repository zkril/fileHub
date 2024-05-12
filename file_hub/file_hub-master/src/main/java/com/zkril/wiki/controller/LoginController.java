package com.zkril.wiki.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zkril.wiki.mapper.AccountMapper;
import com.zkril.wiki.pojo.Account;
import com.zkril.wiki.service.AccountService;
import com.zkril.wiki.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private AccountMapper accountMapper;

    @PostMapping("/login")
    public Result login(@RequestParam String uname, @RequestParam String password) {

        QueryWrapper<Account> accountQueryWrapper=new QueryWrapper<>();
        accountQueryWrapper.eq("uname", uname);
        Account account=accountMapper.selectOne(accountQueryWrapper);
        if (account == null) {
            return Result.fail("用户名不存在");
        }
        else {
            if (!password.equals(account.getAccountPassword())) {
                return Result.fail("密码错误");
            }
            else return Result.ok(account);
        }
    }

    @PostMapping("/checkin")
    public Result checkIn(@RequestParam String uname, @RequestParam String password) {
        if (uname == null || password == null) {
            return Result.fail("缺少必要的字段");
        }
        QueryWrapper<Account> accountQueryWrapper=new QueryWrapper<>();
        accountQueryWrapper.eq("uname", uname);
        Account account=accountMapper.selectOne(accountQueryWrapper);
        if (account == null) {
            Account account2 = new Account();
            account2.setUname(uname);
            account2.setAccountPassword(password);

            if (accountMapper.insert(account2)!=0) {
                return Result.ok("注册成功");
            } else {
                return Result.fail("注册失败");
            }
        }
        else return Result.fail("用户已经被注册");
    }
}
