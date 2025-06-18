package com.javaweb.mystiacanteen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaweb.mystiacanteen.entity.AllSelection;
import com.javaweb.mystiacanteen.entity.Page;

import java.util.List;

public interface AllSelectionService extends IService<AllSelection> {
    public List<AllSelection> getAllSelection(Page page);
    public List<AllSelection> getAllSelectionByName(String name,Page page);
    public List<AllSelection> getAllSelectionHot(Page page);
    public List<AllSelection> getAllSelectionGood(Page page);
    public List<AllSelection> getAllSelectionByTag(String Tag,Page page);
}
