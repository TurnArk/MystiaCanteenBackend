package com.javaweb.mystiacanteen.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaweb.mystiacanteen.entity.Food;
import com.javaweb.mystiacanteen.entity.Page;
import com.javaweb.mystiacanteen.mapper.FoodMapper;
import com.javaweb.mystiacanteen.service.FoodService;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements FoodService {
    @Autowired
    private FoodMapper foodMapper;

    @Override
    public List<Food> getAllFood(Page page){
        List<Food> result =  foodMapper.getAllFood();
        int startIndex = (page.getIndex() - 1) * page.getSize();
        int endIndex = Math.min(startIndex + page.getSize(), result.size());
        result=result.subList(startIndex, endIndex);
        return result;
    }

    @Override
    public List<Food> getFoodByFoodName(String foodName){
        return foodMapper.getFoodByName(foodName);
    }

    @Override
    public Food getFood(String foodName){
        foodMapper.updateClick(foodName);
        return foodMapper.selectFoodByName(foodName);
    }

    @Override
    public void insertFood(){
        String filePath="D:\\用户\\桌面\\期末大作业\\mystiaSpider\\Data\\food.csv";
        ObjectMapper objectMapper = new ObjectMapper();

        try(CSVReader br = new CSVReader(new FileReader(filePath, StandardCharsets.UTF_8))){
            String[] parts;
            while((parts=br.readNext())!=null){
                Food food=new Food();
                food.setId(Integer.parseInt(parts[0]));
                food.setName(parts[1]);
                food.setImage(parts[2]);
                food.setPosition(parts[3]);
                food.setDescription(parts[4]);
                food.setPrice(Integer.parseInt(parts[5]));
                food.setTags(objectMapper.readTree(parts[6]));
                food.setClick(0);
                food.setBuy(0);
                foodMapper.insertFood(food);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
