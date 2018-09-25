package com.kkb.mybatis.dao;

import com.kkb.mybatis.po.User;

public interface UserDao {
	public User findUserById(int id) throws Exception;
	public void insertUser(User user) throws Exception;
}
