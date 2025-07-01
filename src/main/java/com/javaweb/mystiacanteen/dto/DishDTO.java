package com.javaweb.mystiacanteen.dto;

import com.javaweb.mystiacanteen.entity.Dish;
import lombok.Data;

@Data
public class DishDTO extends Dish {
    private Integer number;
}
