package com.kkb.mybatis.mapper;

import java.util.List;

import com.kkb.mybatis.po.OrdersExt;
import com.kkb.mybatis.po.User;

public interface UserMapper {
    User findUserById(int id) throws Exception;

    int insertUser(User user) throws Exception;

    // 测试mybatis延迟加载
    List<OrdersExt> testLazyLoading() throws Exception;
}
