package com.javaweb.mystiacanteen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaweb.mystiacanteen.entity.Dish;
import com.javaweb.mystiacanteen.handler.JsonNodeTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
    @Select("SELECT * FROM dish")
    List<Dish> getAllDish();

    @Select("SELECT * FROM dish WHERE name LIKE CONCAT('%', #{name}, '%') ")
    List<Dish> getDishByName(String name);

    @Select("SELECT * FROM dish WHERE name = #{name}")
    Dish selectDishByName(String name);//专供购物车功能用的

    @Insert("INSERT INTO dish VALUES (#{id},#{name},#{description},#{price},#{image},#{tags},#{position},#{click},#{buy})")
    void insertDish(Dish dish);

    @Update("UPDATE dish SET click=click+1 WHERE name=#{name}")
    void updateClick(String name);

    @Update("UPDATE dish SET buy=buy=1 WHERE name=#{name}")
    void updateBuy(String name);
}
