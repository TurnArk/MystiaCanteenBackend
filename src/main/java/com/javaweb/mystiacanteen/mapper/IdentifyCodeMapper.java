package com.javaweb.mystiacanteen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaweb.mystiacanteen.entity.IdentifyCode;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IdentifyCodeMapper extends BaseMapper<IdentifyCode> {
    @Insert("INSERT INTO identify_code(email,code,start_time,out_time) VALUES (#{email},#{code},#{start_time},#{out_time})")
    Boolean addCode(String email, String code, long start_time, long out_time);

    @Delete("DELETE FROM identify_code WHERE out_time<=#{now_time}")
    void delCode(long now_time);

    @Select("SELECT code FROM identify_code WHERE email=#{email}")
    String getCode(String email);
}
