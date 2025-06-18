package com.javaweb.mystiacanteen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaweb.mystiacanteen.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    public Boolean login(String username,String password);
    public Boolean addUser(String username, String password, String name, String gender, String email);
    public Boolean updatePassword(String username, String password,String code);
    public String mailCode(String email);
    public Boolean checkCode(String email,String code);
    public List<String> getUser(String username);
}
