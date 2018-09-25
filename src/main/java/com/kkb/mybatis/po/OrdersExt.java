package com.kkb.mybatis.po;

public class OrdersExt extends Orders {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
