package com.javaweb.mystiacanteen.controller;

import com.javaweb.mystiacanteen.entity.Kitchenware;
import com.javaweb.mystiacanteen.entity.Page;
import com.javaweb.mystiacanteen.service.KitchenwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KitchenwareController {
    @Autowired
    private KitchenwareService kitchenwareService;

    @PostMapping("/kitchenware")
    public List<Kitchenware> getAllKitchenware(@RequestBody Page page) {
        return kitchenwareService.getAllKitchenware(page);
    }

    @GetMapping("/putKitchenware")
    public void putKitchenware() {
        kitchenwareService.insertKitchenware();
    }

    @GetMapping("/getKitchenware/{name}")
    public Kitchenware getKitchenware(@PathVariable String name) {
        return kitchenwareService.getKitchenware(name);
    }
}
