package com.javaweb.mystiacanteen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaweb.mystiacanteen.entity.Cart;
import com.javaweb.mystiacanteen.entity.CartData;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper extends BaseMapper<Cart> {
    @Select("SELECT name,num,type,cartId FROM cart WHERE username=#{username}")
    List<CartData> getAllProductName(String username);//得到某个用户的购物车数据

    @Select("SELECT cartId FROM cart WHERE username=#{username} AND name=#{name}")
    Integer findCartId(String username, String name);//找到要修改的项的ID

    @Update("UPDATE cart SET num = num + #{num} WHERE cartId=#{cartId}")
    Boolean updateNum(Integer num, Integer cartId);//修改存在的商品的数量

    @Insert("INSERT INTO cart(username,name,num,type) VALUES (#{username},#{name},#{num},#{type})")
    Boolean addCart(String username, String name, int num,String type);//添加不存在的商品;

    @Delete("DELETE FROM cart WHERE username=#{username} AND name=#{name}")
    Boolean deleteCart(String username, String name);
}
