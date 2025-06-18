package com.javaweb.mystiacanteen.controller;

import com.javaweb.mystiacanteen.entity.AllSelection;
import com.javaweb.mystiacanteen.entity.Page;
import com.javaweb.mystiacanteen.service.AllSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AllSelectionController {
    @Autowired
    private AllSelectionService allSelectionService;

    @PostMapping("/selectionAll")
    public List<AllSelection> selectionAll(@RequestBody Page page){
        return allSelectionService.getAllSelection(page);
    }

    @PostMapping("/selectionByName/{name}")
    public List<AllSelection> selectionByName(@PathVariable String name,
                                              @RequestBody Page page){
        return allSelectionService.getAllSelectionByName(name,page);
    }
    @PostMapping("/selectionAllHot")
    public List<AllSelection> selectionAllHot(@RequestBody Page page){
        return allSelectionService.getAllSelectionHot(page);
    }
    @PostMapping("/selectionAllGood")
    public List<AllSelection> selectionAllGood(@RequestBody Page page){
        return allSelectionService.getAllSelectionGood(page);
    }
    @PostMapping("/selectionTag/{tag}")
    public List<AllSelection> selectionTag(@PathVariable String tag,@RequestBody Page page){
        return allSelectionService.getAllSelectionByTag(tag,page);
    }
}
