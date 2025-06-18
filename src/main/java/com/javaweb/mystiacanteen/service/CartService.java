package com.javaweb.mystiacanteen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaweb.mystiacanteen.entity.Cart;
import com.javaweb.mystiacanteen.entity.ProductionData;

import java.util.List;

public interface CartService extends IService<Cart> {
    public List<ProductionData> getAllCart(String username);
    public Boolean RenewCart(String username, String name, int num,String type);
    public Boolean DeleteCart(String username, String name);
}
