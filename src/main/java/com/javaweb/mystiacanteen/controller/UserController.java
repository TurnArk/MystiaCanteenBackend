package com.javaweb.mystiacanteen.controller;

import com.javaweb.mystiacanteen.entity.User;
import com.javaweb.mystiacanteen.filter.JwtFilter;
import com.javaweb.mystiacanteen.mapper.UserMapper;
import com.javaweb.mystiacanteen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if(userService.login(username, password)) {
            String token= JwtFilter.generateToken(username);
            return token;
        }else{
            return null;
        }
    }

    @PostMapping("/register")
    public String register(@RequestBody User user,
                           @RequestParam String code) {
        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        String name = user.getName();
        String gender = user.getGender();
        if(userMapper.getPassword(username)!=null){
            return "账号已存在";
        }
        if(userService.checkCode(email, code)) {
            System.out.println("你验证码是对的");
            if(userService.addUser(username, password, name, gender, email)) {
                return "redirect:/login";
            }
        }
        return "注册失败";
    }

    @PostMapping("/changePassword")
    public Boolean changePassword(@RequestBody User user,
                                 @RequestParam String code){
        String username = user.getUsername();
        String password = user.getPassword();
        if (userService.updatePassword(username, password,code)) {
            return true;
        }else{
            return false;
        }
    }

    @PostMapping("/sendCodePost")
    public String mailCode(@RequestBody User user){
        String email = user.getEmail();
        return userService.mailCode(email);
    }

    @GetMapping("/getUser/{username}")
    public List<String> getUser(@PathVariable String username){
        return userService.getUser(username);
    }
}
