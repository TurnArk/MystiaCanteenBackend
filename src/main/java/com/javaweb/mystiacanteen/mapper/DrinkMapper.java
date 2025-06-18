package com.javaweb.mystiacanteen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaweb.mystiacanteen.entity.Drink;
import com.javaweb.mystiacanteen.handler.JsonNodeTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DrinkMapper extends BaseMapper<Drink> {
    @Select("SELECT * FROM drink")
    List<Drink> getAllDrink();

    @Select("SELECT * FROM drink WHERE name LIKE CONCAT('%',#{name},'%')")
    List<Drink> getDrinkByName(String name);

    @Select("SELECT * FROM drink WHERE name = #{name}")
    Drink selectDrinkByName(String name);//专供购物车功能用的

    @Insert("INSERT INTO drink VALUES (#{id},#{name},#{description},#{price},#{image},#{tags},#{position},#{click},#{buy})")
    void insertDrink(Drink drink);

    @Update("UPDATE dish SET click=click+1 WHERE name=#{name}")
    void updateClick(String name);

    @Update("UPDATE dish SET buy=buy=1 WHERE name=#{name}")
    void updateBuy(String name);
}
