package com.javaweb.mystiacanteen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.javaweb.mystiacanteen.entity.Order;
import com.javaweb.mystiacanteen.handler.JsonNodeTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Select("SELECT * FROM `order` WHERE username = #{username} ORDER BY start_time DESC")
    List<Order> findByUsername(String username);

    @Update("UPDATE `order` SET status=2 WHERE id=#{id}")
    Boolean deleteById(Integer id);

    @Select("SELECT id FROM `order` WHERE out_time>#{now_time} AND username=#{username} AND message=#{message}")
    Integer findIdAsUser(String username, JsonNode message,long now_time);

    @Update("UPDATE `order` SET status=2 WHERE out_time<=#{now_time}")
    void clearOrder(long now_time);

    @Insert("INSERT INTO `order`(username,message,start_time,out_time,status) " +
            "VALUES (#{username},#{message},#{start_time},#{out_time},0)")
    Boolean createOrder(String username, JsonNode message, long start_time, long out_time);

    @Update("UPDATE `order` SET status=1 WHERE id=#{id}")
    Boolean buyBuId(Integer id);
}
