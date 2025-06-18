package com.javaweb.mystiacanteen.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaweb.mystiacanteen.entity.*;
import com.javaweb.mystiacanteen.mapper.*;
import com.javaweb.mystiacanteen.service.AllSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AllSelectionServiceImpl extends ServiceImpl<AllSelectionMapper,AllSelection> implements AllSelectionService {
    @Autowired
    DishMapper dishMapper;
    @Autowired
    DrinkMapper drinkMapper;
    @Autowired
    FoodMapper foodMapper;
    @Autowired
    KitchenwareMapper kitchenwareMapper;
    @Autowired
    AllSelectionMapper allSelectionMapper;

    @Override
    public List<AllSelection> getAllSelection(Page page) {
        List<Production> list = new ArrayList<Production>();
        list.addAll(dishMapper.getAllDish());
        list.addAll(drinkMapper.getAllDrink());
        list.addAll(foodMapper.getAllFood());
        list.addAll(kitchenwareMapper.getAllKitchenware());

        List<AllSelection> result = new ArrayList<AllSelection>();
        for (Production production : list) {
            AllSelection selection = new AllSelection();
            selection.setId(production.getId());
            selection.setName(production.getName());
            selection.setPrice(production.getPrice());
            selection.setDescription(production.getDescription());
            selection.setImage(production.getImage());
            selection.setPosition(production.getPosition());

            // 如果子类有 tags 属性，提取 tags
            if (production instanceof Dish || production instanceof Drink || production instanceof Food) {
                selection.setTags(production.getTags());
            } else {
                selection.setTags(null); // 如果没有 tags，设为 null
            }
            result.add(selection);
        }
        int startIndex = (page.getIndex() - 1) * page.getSize();
        int endIndex = Math.min(startIndex + page.getSize(), result.size());
        result=result.subList(startIndex, endIndex);
        return result;
    }

    @Override
    public List<AllSelection> getAllSelectionByName(String name,Page page) {
        List<Production> list = new ArrayList<Production>();
        list.addAll(dishMapper.getDishByName(name));
        list.addAll(drinkMapper.getDrinkByName(name));
        list.addAll(foodMapper.getFoodByName(name));
        list.addAll(kitchenwareMapper.getKitchenwareByName(name));

        List<AllSelection> result = new ArrayList<AllSelection>();
        for (Production production : list) {
            AllSelection selection = new AllSelection();
            selection.setId(production.getId());
            selection.setName(production.getName());
            selection.setPrice(production.getPrice());
            selection.setDescription(production.getDescription());
            selection.setImage(production.getImage());
            selection.setPosition(production.getPosition());

            if (production instanceof Dish || production instanceof Drink || production instanceof Food) {
                selection.setTags(production.getTags());
            } else {
                selection.setTags(null); // 如果没有 tags，设为 null
            }
            result.add(selection);
        }
        int startIndex = (page.getIndex() - 1) * page.getSize();
        int endIndex = Math.min(startIndex + page.getSize(), result.size());
        result=result.subList(startIndex, endIndex);
        return result;
    }

    @Override
    public List<AllSelection> getAllSelectionHot(Page page){
        List<AllSelection> result = allSelectionMapper.selectAllSelectionHot();
        int startIndex = (page.getIndex() - 1) * page.getSize();
        int endIndex = Math.min(startIndex + page.getSize(), result.size());
        result=result.subList(startIndex, endIndex);
        return result;
    }

    @Override
    public List<AllSelection> getAllSelectionGood(Page page){
        List<AllSelection> result = allSelectionMapper.selectAllSelectionGood();
        int startIndex = (page.getIndex() - 1) * page.getSize();
        int endIndex = Math.min(startIndex + page.getSize(), result.size());
        result=result.subList(startIndex, endIndex);
        return result;
    }

    @Override
    public List<AllSelection> getAllSelectionByTag(String tags,Page page){
        ObjectMapper objectMapper = new ObjectMapper();
        List<AllSelection> data=allSelectionMapper.selectionAll();
        List<AllSelection> result = new ArrayList<>();
        for (AllSelection item : data) {
            JsonNode json = item.getTags();
            if(json!=null){
                json=json.get("truly");
                List<String> tagsList = objectMapper.convertValue(json, List.class);
                if(tagsList.contains(tags)){
                    result.add(item);
                }
            }
        }
        int startIndex = (page.getIndex() - 1) * page.getSize();
        int endIndex = Math.min(startIndex + page.getSize(), result.size());
        result=result.subList(startIndex, endIndex);
        return result;
    }
}
