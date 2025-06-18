package com.javaweb.mystiacanteen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaweb.mystiacanteen.entity.Kitchenware;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface KitchenwareMapper extends BaseMapper<Kitchenware> {
    @Select("SELECT * FROM kitchenware")
    List<Kitchenware> getAllKitchenware();

    @Select("SELECT * FROM kitchenware WHERE name LIKE CONCAT('%',#{name},'%')")
    List<Kitchenware> getKitchenwareByName(String name);

    @Select("SELECT * FROM Kitchenware WHERE name = #{name}")
    Kitchenware selectKitchenwareByName(String name);//专供购物车功能用的

    @Insert("INSERT INTO kitchenware VALUES (#{id},#{name},#{description},#{price},#{image},#{position},#{click},#{buy})")
    void insertKitchenware(Kitchenware kitchenware);

    @Update("UPDATE dish SET click=click+1 WHERE name=#{name}")
    void updateClick(String name);

    @Update("UPDATE dish SET buy=buy=1 WHERE name=#{name}")
    void updateBuy(String name);
}
