package com.javaweb.mystiacanteen.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaweb.mystiacanteen.entity.Kitchenware;
import com.javaweb.mystiacanteen.entity.Page;
import com.javaweb.mystiacanteen.mapper.KitchenwareMapper;
import com.javaweb.mystiacanteen.service.KitchenwareService;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class KitchenwareServiceImpl extends ServiceImpl<KitchenwareMapper, Kitchenware> implements KitchenwareService {
    @Autowired
    private KitchenwareMapper kitchenwareMapper;

    @Override
    public List<Kitchenware> getAllKitchenware(Page page) {
        List<Kitchenware> result = kitchenwareMapper.getAllKitchenware();
        int startIndex = (page.getIndex() - 1) * page.getSize();
        int endIndex = Math.min(startIndex + page.getSize(), result.size());
        result=result.subList(startIndex, endIndex);
        return result;
    }

    @Override
    public List<Kitchenware> getKitchenwareByName(String name) {
        return kitchenwareMapper.getKitchenwareByName(name);
    }

    @Override
    public Kitchenware getKitchenware(String name){
        kitchenwareMapper.updateClick(name);
        return kitchenwareMapper.selectKitchenwareByName(name);
    }

    @Override
    public void insertKitchenware() {
        String filePath="D:\\用户\\桌面\\期末大作业\\mystiaSpider\\Data\\kitchenware.csv";
        ObjectMapper objectMapper = new ObjectMapper();

        try(CSVReader br = new CSVReader(new FileReader(filePath, StandardCharsets.UTF_8))){
            String[] parts;
            while((parts=br.readNext())!=null){
                Kitchenware kitchenware=new Kitchenware();
                kitchenware.setId(Integer.parseInt(parts[0]));
                kitchenware.setName(parts[1]);
                kitchenware.setImage(parts[2]);
                kitchenware.setPosition(parts[3]);
                kitchenware.setDescription(parts[4]);
                kitchenware.setPrice(Integer.parseInt(parts[5]));
                kitchenware.setClick(0);
                kitchenware.setBuy(0);
                kitchenwareMapper.insertKitchenware(kitchenware);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
