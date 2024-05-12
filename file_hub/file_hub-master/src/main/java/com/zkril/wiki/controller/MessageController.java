package com.zkril.wiki.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zkril.wiki.mapper.MessageMapper;
import com.zkril.wiki.mapper.MessagefileMapper;
import com.zkril.wiki.pojo.Account;
import com.zkril.wiki.pojo.Course;
import com.zkril.wiki.pojo.Message;
import com.zkril.wiki.pojo.Messagefile;
import com.zkril.wiki.util.Result;
import com.zkril.wiki.util.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    MessagefileMapper messagefileMapper;
    @GetMapping(value = "")
    public Result getmessagelist(String name){
        QueryWrapper<Message> messageQueryWrapper=new QueryWrapper<>();
        messageQueryWrapper.like("message_name","%"+name+"%");
        List<Message> messageList=messageMapper.selectList(messageQueryWrapper);
        return Result.ok(messageList);
    }
    @PostMapping(value="/change")
    public Result changeMessage(@RequestParam Map<String,Object> message) throws ParseException {
        System.out.println(message);
        Message message1=new Message();
        message1.setMessageBody(message.get("message_body").toString());
        message1.setMessageName(message.get("message_name").toString());
        message1.setMid(message.get("mid").toString());
        message1.setDdl(message.get("ddl").toString());
        message1.setMessageid(message.get("messageid").toString());
        message1.setCourseid(Integer.valueOf(message.get("courseid").toString()));
        message1.setAccountid(Integer.valueOf(message.get("accountid").toString()));
        if(messageMapper.updateById(message1)!=0)
            return Result.ok();
        else
            return Result.fail();
    }
    @PostMapping(value="/add")
    public Result addMessage(@RequestParam Map<String,Object> message) throws ParseException {
        System.out.println(message);
        if(message.get("message_name")==null ||message.get("accountid")==null||message.get("courseid")==null)
            return Result.fail("缺少必要的字段");
        Message message1=new Message();
        message1.setMessageBody(message.get("message_body").toString());
        message1.setMessageName(message.get("message_name").toString());
        message1.setMid(message.get("mid").toString());
        message1.setDdl(message.get("ddl").toString());
        message1.setCourseid(Integer.valueOf(message.get("courseid").toString()));
        message1.setAccountid(Integer.valueOf(message.get("accountid").toString()));
        if(messageMapper.insert(message1)>=0)
            return Result.ok();
        else
            return Result.fail();
    }
    @DeleteMapping(value = "delete")
    public Result deleteMessage(String messageid)  {
        System.out.println("aaaaa");
        System.out.println(messageid);
        if (messageid == null) {
            return Result.fail("缺少作业id");
        }

        if (messageMapper.deleteById(messageid)!=0) {
            return Result.ok("删除作业成功");
        } else {
            return Result.fail("删除作业失败");
        }
    }
    @GetMapping( "/file/search")
    public Result getfilelist(String messageid)
    {
        System.out.println(messageid);
        QueryWrapper<Messagefile> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("messageid",messageid);
        List<Messagefile> messageList=messagefileMapper.selectList(queryWrapper);
        return Result.ok(messageList);
    }

    @GetMapping("/file/download")
    public void downloadFiles(@RequestParam("fileid") String fileid, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(fileid);
        Messagefile messagefile = messagefileMapper.selectById(fileid);

        if (messagefile != null) {
            String realPath;
            try {
                realPath = ResourceUtils.getURL("classpath:").getPath() + "/static" + messagefile.getPath()+'/'+messagefile.getNewname();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            OutputStream outputStream = null;
            InputStream inputStream = null;
            BufferedInputStream bufferedInputStream = null;
            byte[] bytes = new byte[1024];
            File file = new File(realPath);
            String fileName = file.getName();
            // 获取输出流
            try {
                response.setHeader("Access-Control-Expose-Headers","Content-Disposition");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(messagefile.getOldname(), "UTF-8"));

                // 以流的形式返回文件
                response.setContentType("application/octet-stream;charset=utf-8");
                inputStream = new FileInputStream(file);
                bufferedInputStream = new BufferedInputStream(inputStream);
                outputStream = response.getOutputStream();
                int i = bufferedInputStream.read(bytes);
                while (i != -1) {
                    outputStream.write(bytes, 0, i);
                    i = bufferedInputStream.read(bytes);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (bufferedInputStream != null) {
                        bufferedInputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
