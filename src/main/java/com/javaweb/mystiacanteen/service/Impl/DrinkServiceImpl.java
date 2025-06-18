package com.javaweb.mystiacanteen.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaweb.mystiacanteen.entity.Dish;
import com.javaweb.mystiacanteen.entity.Drink;
import com.javaweb.mystiacanteen.entity.Page;
import com.javaweb.mystiacanteen.mapper.DrinkMapper;
import com.javaweb.mystiacanteen.service.DrinkService;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class DrinkServiceImpl extends ServiceImpl<DrinkMapper, Drink> implements DrinkService {
    @Autowired
    private DrinkMapper drinkMapper;

    @Override
    public List<Drink> getAllDrink(Page page){
        List<Drink> result=drinkMapper.getAllDrink();
        int startIndex = (page.getIndex() - 1) * page.getSize();
        int endIndex = Math.min(startIndex + page.getSize(), result.size());
        result=result.subList(startIndex, endIndex);
        return result;
    }

    @Override
    public List<Drink> getDrinkByName(String drinkName){
        return drinkMapper.getDrinkByName(drinkName);
    }

    @Override
    public Drink getDrink(String drinkName){
        drinkMapper.updateClick(drinkName);
        return drinkMapper.selectDrinkByName(drinkName);
    }

    @Override
    public void insertDrink(){
        String filePath="D:\\用户\\桌面\\期末大作业\\mystiaSpider\\Data\\drink.csv";
        ObjectMapper objectMapper = new ObjectMapper();

        try(CSVReader br = new CSVReader(new FileReader(filePath, StandardCharsets.UTF_8))){
            String[] parts;
            while((parts=br.readNext())!=null){
                Drink drink = new Drink();
                drink.setId(Integer.parseInt(parts[0]));
                drink.setName(parts[1]);
                drink.setImage(parts[2]);
                drink.setPosition(parts[3]);
                drink.setDescription(parts[4]);
                drink.setPrice(Integer.parseInt(parts[5]));
                drink.setTags(objectMapper.readTree(parts[6]));
                drink.setClick(0);
                drink.setBuy(0);
                drinkMapper.insertDrink(drink);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
