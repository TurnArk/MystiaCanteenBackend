package com.javaweb.mystiacanteen.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaweb.mystiacanteen.entity.Dish;
import com.javaweb.mystiacanteen.entity.Page;
import com.javaweb.mystiacanteen.mapper.DishMapper;
import com.javaweb.mystiacanteen.service.DishService;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired DishMapper dishMapper;

    @Override
    public List<Dish> getAllDish(Page page){
        List<Dish> result=dishMapper.getAllDish();
        int startIndex = (page.getIndex() - 1) * page.getSize();
        int endIndex = Math.min(startIndex + page.getSize(), result.size());
        result=result.subList(startIndex, endIndex);
        return result;
    }

    @Override
    public List<Dish> getDishByName(String dishName){
        return dishMapper.getDishByName(dishName);
    }

    @Override
    public Dish getDish(String dishName){
        dishMapper.updateClick(dishName);
        return dishMapper.selectDishByName(dishName);
    }

    @Override
    public void insertDish(){
        String filePath="D:\\用户\\桌面\\期末大作业\\mystiaSpider\\Data\\dish.csv";
        ObjectMapper objectMapper = new ObjectMapper();

        try(CSVReader br = new CSVReader(new FileReader(filePath, StandardCharsets.UTF_8))){
            String[] parts;
            while((parts=br.readNext())!=null){
                System.out.println(parts[0]);
                Dish dish = new Dish();
                dish.setId(Integer.parseInt(parts[0]));
                dish.setName(parts[1]);
                dish.setImage(parts[2]);
                dish.setPosition(parts[3]);
                dish.setDescription(parts[4]);
                dish.setPrice(Integer.parseInt(parts[5]));
                dish.setTags(objectMapper.readTree(parts[6]));
                dish.setClick(0);
                dish.setBuy(0);
                dishMapper.insertDish(dish);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
