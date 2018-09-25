package com.kkb.mybatis.po;

import java.util.List;

public class UserExt extends User {
    List<Orders> orders;

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

}
