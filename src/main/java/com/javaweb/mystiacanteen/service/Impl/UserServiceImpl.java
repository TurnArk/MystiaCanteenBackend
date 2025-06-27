package com.javaweb.mystiacanteen.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaweb.mystiacanteen.entity.User;
import com.javaweb.mystiacanteen.mapper.UserMapper;
import com.javaweb.mystiacanteen.service.IdentifyCodeService;
import com.javaweb.mystiacanteen.service.PasswordService;
import com.javaweb.mystiacanteen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class  UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IdentifyCodeService identifyCodeService;

    @Autowired
    private Properties mailProperties;

    @Value("${mail.from}")
    private String from;

    @Value("${mail.password}")
    private String password;

    @Override
    public Boolean login(String username,String password) {
        String pass = userMapper.getPassword(username);
        PasswordService passwordService = new PasswordService();
        return passwordService.matchPassword(password,pass);
    }

    @Override
    public Boolean addUser(String username, String password, String name, String gender, String email){
        PasswordService passwordService = new PasswordService();
        String encodePassword = passwordService.encodePassword(password);
        return userMapper.addUser(username, encodePassword, name, gender, email);
    }

    @Override
    public Boolean updatePassword(String username,String password,String code){
        String email = userMapper.getEmail(username);
        mailCode(email);
        if(checkCode(email,code)){
            PasswordService passwordService = new PasswordService();
            String encodePassword = passwordService.encodePassword(password);
            return userMapper.updatePassword(username, encodePassword);
        }
        else
            return false;
    }

    @Override
    public String mailCode(String email){//发送验证码邮件
//        String host = "smtp.qq.com";
//        String from = "your_email";
//        String password = "your_email_password";
//
//        Properties props = new Properties();
//        props.put("mail.smtp.host", host);
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.ssl.enable", "true");
//        props.put("mail.smtp.port", "465");
//        props.put("mail.smtp.connectiontimeout", "5000");  // 连接超时
//        props.put("mail.smtp.timeout", "5000");  // 读取超时

        Session session = Session.getInstance(mailProperties,new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email));
            message.setSubject("夜雀食堂在线网站验证码");
            if(identifyCodeService.addCode(email)){
                String code = identifyCodeService.getCode(email);
                String text="您用来访问夜雀食堂在线购物网站的验证码为: \n"+code+"\n五分钟后失效。";
                message.setText(text);
                Transport.send(message);
                return "验证码已发送";
            }
            else{
                return "验证码发送失败";
            }
        }catch (MessagingException e){
            e.printStackTrace();
            log.error("邮件发送失败: " + e.getMessage());
            return "验证码发送失败";
        }
    }

    @Override
    public Boolean checkCode(String email,String code){
        return code.equals(identifyCodeService.getCode(email));
    }

    @Override
    public List<String> getUser(String username){
        List<String> list = new ArrayList<>();
        User user = userMapper.getUser(username);
        list.add(user.getUsername());
        list.add(user.getEmail());
        list.add(user.getName());
        list.add(user.getGender());
        list.add(user.getDeposit().toString());
        return list;
    }
}
