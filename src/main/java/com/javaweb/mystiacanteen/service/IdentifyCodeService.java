package com.javaweb.mystiacanteen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaweb.mystiacanteen.entity.IdentifyCode;

public interface IdentifyCodeService extends IService<IdentifyCode> {
    public void clearOldCode();
    public String getCode(String email);
    public Boolean addCode(String email);
}
