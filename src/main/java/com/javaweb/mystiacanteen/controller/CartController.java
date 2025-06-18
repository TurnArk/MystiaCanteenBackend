package com.javaweb.mystiacanteen.controller;

import com.javaweb.mystiacanteen.entity.Cart;
import com.javaweb.mystiacanteen.entity.ProductionData;
import com.javaweb.mystiacanteen.filter.JwtFilter;
import com.javaweb.mystiacanteen.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/getCart/{username}")
    public List<ProductionData> getCart(@PathVariable String username) {
        return cartService.getAllCart(username);
    }

    @PostMapping("/renew")
    public String renewCart(@RequestBody Cart cart, HttpServletRequest request) {
        String username = cart.getUsername();
        String name=cart.getName();
        int num = cart.getNum();
        String type = cart.getType();
        if(JwtFilter.validateToken(request,username)){
            if(cartService.RenewCart(username, name, num,type)) {
                return "修改购物车成功";
            }
            return "修改购物车失败";
        }
        return "/login";
    }

    @PostMapping("/deleteCart")
    public String deleteCart(@RequestBody Cart cart) {
        String username = cart.getUsername();
        String name=cart.getName();
        if(cartService.DeleteCart(username, name)) {
            return "删除成功";
        }
        return "删除失败";
    }
}
