package com.javaweb.mystiacanteen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaweb.mystiacanteen.entity.TalkMessage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TalkMessageMapper extends BaseMapper<TalkMessage> {
    @Select("SELECT * FROM talk_message WHERE talk_with=#{talk_with} AND type=#{type} AND productionId = #{productionId}")
    List<TalkMessage> getMessage(String talk_with, String type, int productionId);

    @Delete("DELETE FROM talk_message WHERE id=#{id}")
    Boolean deleteMessage(int id);

    @Insert("INSERT INTO talk_message(username,message,talk_with,productionId,type,time) " +
            "VALUES (#{username},#{message},#{talk_with},#{productionId},#{type},#{time})")
    Boolean addMessage(String username, String message, String talk_with, int productionId, String type, LocalDateTime time);
}
