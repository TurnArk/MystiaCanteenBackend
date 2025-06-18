package com.javaweb.mystiacanteen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.JsonNode;
import com.javaweb.mystiacanteen.entity.Kitchenware;
import com.javaweb.mystiacanteen.entity.Page;

import java.util.List;

public interface KitchenwareService extends IService<Kitchenware> {
    public List<Kitchenware> getAllKitchenware(Page page);
    public List<Kitchenware> getKitchenwareByName(String name);
    public void insertKitchenware();
    public Kitchenware getKitchenware(String name);
}
