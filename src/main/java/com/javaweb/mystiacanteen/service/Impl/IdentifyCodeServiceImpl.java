package com.javaweb.mystiacanteen.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaweb.mystiacanteen.entity.IdentifyCode;
import com.javaweb.mystiacanteen.mapper.IdentifyCodeMapper;
import com.javaweb.mystiacanteen.service.IdentifyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class IdentifyCodeServiceImpl extends ServiceImpl<IdentifyCodeMapper, IdentifyCode> implements IdentifyCodeService {
    @Autowired
    private IdentifyCodeMapper identifyCodeMapper;

    @Override
    public void clearOldCode(){
        long now_time=System.currentTimeMillis();
        identifyCodeMapper.delCode(now_time);
    }

    @Override
    public String getCode(String email){
        clearOldCode();
        return identifyCodeMapper.getCode(email);
    }

    @Override
    public Boolean addCode(String email){
        if(getCode(email)==null){
            String code = createCode();
            long start_time=System.currentTimeMillis();
            long out_time=start_time+1000*60*5;
            identifyCodeMapper.addCode(email,code,start_time,out_time);
            return true;
        }else{
            return false;
        }
    }

    // 生成随机验证码
    private static String createCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }
        return code.toString();
    }

}
