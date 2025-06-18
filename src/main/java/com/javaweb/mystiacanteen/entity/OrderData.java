package com.javaweb.mystiacanteen.entity;

public class OrderData extends ProductionData{
    private int status;
    private int orderId;

    @Override
    public int getOrderId() {
        return orderId;
    }

    @Override
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }
}
