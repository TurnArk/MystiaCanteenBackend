package com.javaweb.mystiacanteen.entity;

import lombok.Data;

@Data
public class ProductionData {
    protected Production production;
    protected int num;
    protected int cartId;
    public int getStatus() {
        return 0;
    }

    public void setStatus(int status) {
    }

    public int getOrderId() {
        return 0;
    }


    public void setOrderId(int orderId) {
    }
}
