package com.javaweb.mystiacanteen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaweb.mystiacanteen.entity.Food;
import com.javaweb.mystiacanteen.handler.JsonNodeTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FoodMapper extends BaseMapper<Food> {
    @Select("SELECT * FROM food")
    List<Food> getAllFood();

    @Select("SELECT * FROM food WHERE name LIKE CONCAT('%',#{name},'%')")
    List<Food> getFoodByName(String name);

    @Select("SELECT * FROM food WHERE name = #{name}")
    Food selectFoodByName(String name);//专供购物车功能用的

    @Insert("INSERT INTO food VALUES (#{id},#{name},#{description},#{price},#{image},#{tags},#{position},#{click},#{buy})")
    void insertFood(Food food);

    @Update("UPDATE dish SET click=click+1 WHERE name=#{name}")
    void updateClick(String name);

    @Update("UPDATE dish SET buy=buy=1 WHERE name=#{name}")
    void updateBuy(String name);
}
