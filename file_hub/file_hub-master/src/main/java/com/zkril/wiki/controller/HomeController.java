package com.zkril.wiki.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zkril.wiki.mapper.AccountMapper;
import com.zkril.wiki.mapper.CourseMapper;
import com.zkril.wiki.mapper.MessageMapper;
import com.zkril.wiki.mapper.MessagefileMapper;
import com.zkril.wiki.pojo.Account;
import com.zkril.wiki.pojo.Course;
import com.zkril.wiki.pojo.Message;
import com.zkril.wiki.pojo.Messagefile;
import com.zkril.wiki.util.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    MessagefileMapper messagefileMapper;
    @GetMapping( "/message/search")
    public Result getmessagelist( String uname,  String coursename)
    {
        System.out.println(uname);
        System.out.println(coursename);
        QueryWrapper<Account> accountQueryWrapper=new QueryWrapper<>();
        accountQueryWrapper.eq("uname",uname);
        List<Account> accountList=accountMapper.selectList(accountQueryWrapper);
        QueryWrapper<Message> messageQueryWrapper=new QueryWrapper<>();
        if(accountList.size()!=0)
        {
            Integer countid=Integer.valueOf(accountList.get(0).getCountid().toString());
            messageQueryWrapper.eq("accountid",countid);
            if(!coursename.equals(""))
            {
                QueryWrapper<Course> courseQueryWrapper=new QueryWrapper<>();
                courseQueryWrapper.eq("course_name",coursename)
                        .eq("accountid",countid);
                List<Course> courseList=courseMapper.selectList(courseQueryWrapper);
                if(courseList.size()!=0)
                {
                    Integer courseid=Integer.valueOf(courseList.get(0).getCourseid().toString());
                    messageQueryWrapper.eq("courseid",courseid);
                }
            }
        }
        List<Map<String,Object>> messageList=messageMapper.selectMaps(messageQueryWrapper);
        for(Map<String,Object> map:messageList){
            Integer courseid=Integer.valueOf(map.get("courseid").toString());
            QueryWrapper<Course> courseQueryWrapper=new QueryWrapper<>();
            courseQueryWrapper.eq("courseid",courseid);
            Course course=courseMapper.selectOne(courseQueryWrapper);
            if (course != null) {
                map.put("courseName",course.getCourseName());
            }else {
                map.put("courseName","");
            }
            Integer accountid=Integer.valueOf(map.get("accountid").toString());
            QueryWrapper<Account> accountQueryWrapper1=new QueryWrapper<>();
            accountQueryWrapper1.eq("countid",accountid);
            Account account=accountMapper.selectOne(accountQueryWrapper1);
            if (account != null) {
                map.put("uname",account.getUname());
            }else {
                map.put("uname","");
            }
        }
        return Result.ok(messageList);
    }
    @GetMapping( "/course/search")
    public Result getcourselist( String uname)
    {
        System.out.println(uname);
        QueryWrapper<Account> accountQueryWrapper=new QueryWrapper<>();
        accountQueryWrapper.eq("uname",uname);
        List<Account> accountList=accountMapper.selectList(accountQueryWrapper);
        QueryWrapper<Course> courseQueryWrapper=new QueryWrapper<>();
        if(accountList.size()!=0)
        {
            Integer countid=Integer.valueOf(accountList.get(0).getCountid().toString());
            courseQueryWrapper.eq("accountid",countid);
            List<Course> courseList=courseMapper.selectList(courseQueryWrapper);
            return Result.ok(courseList);
        }
        return Result.fail("学委名称不存在");
    }
    @PostMapping("upload")
    public Result upload(@RequestParam("file") MultipartFile file,@RequestParam("messageid") String messageid) throws IOException {
        System.out.println("aaaa");
        System.out.println(messageid);

        QueryWrapper<Message> messageQueryWrapper=new QueryWrapper<>();
        messageQueryWrapper.eq("messageid",Integer.valueOf(messageid));
        // 获取文件的原始名称
        String oldFileName = file.getOriginalFilename();
        // 生成新的文件名称
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +
                UUID.randomUUID().toString().replace("-", "") + oldFileName;

        // 获取文件的大小
        long size = file.getSize();
        // 获取文件类型
        String type = file.getContentType();

        // 根据日期生成文件目录
        String realPath = ResourceUtils.getURL("classpath:").getPath() + "/static/files";
        // 日期文件夹
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dateDirPath = realPath + "/" + date;
        File dateDir = new File(dateDirPath);
        System.out.println(dateDir.getPath());
        dateDir.mkdirs();

        // 处理文件上传
        file.transferTo(new File(dateDir, newFileName));

        // 将文件信息放入数据库
        Messagefile messagefile = new Messagefile();
        messagefile.setOldname(oldFileName);
        messagefile.setNewname(newFileName);
        messagefile.setFilesize((int) size);
        messagefile.setPath("/files/" + date);
        messagefile.setMessageid(Integer.valueOf(messageid));
        if (messagefileMapper.insert(messagefile)!=0)
            return Result.ok();
        else return Result.fail();
    }
}
