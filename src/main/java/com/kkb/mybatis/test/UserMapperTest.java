package com.kkb.mybatis.test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.kkb.mybatis.mapper.UserMapper;
import com.kkb.mybatis.po.OrdersExt;
import com.kkb.mybatis.po.User;

public class UserMapperTest {

    private SqlSessionFactory sqlSessionFactory;

    /**
     * @Before注解的方法会在@Test注解的方法之前执行
     * 
     * @throws Exception
     */
    @Before
    public void init() throws Exception {
        // 指定全局配置文件路径
        String resource = "SqlMapConfig.xml";
        // 加载资源文件（全局配置文件和映射文件）
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 还有构建者模式，去创建SqlSessionFactory对象
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testInserUser() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setUsername("隔壁老詹1");
        user.setBirthday(new Date());
        user.setSex("1");
        user.setAddress("洛杉矶湖人");
        userMapper.insertUser(user);
        sqlSession.commit();
        User user1 = userMapper.findUserById(user.getId());
        System.out.println(user.getId() + "=============" + user1);
        sqlSession.close();
    }

    // 和Spring整合之后，需要再IoC容器中去管理两个对象：
    // SqlSessionFactory对象/MapperFactoryBean/MapperScannerConfigurer
    @Test
    public void testFindUserById() throws Exception {
        // 构造UserMapper对象（sqlSession）
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 需要传的参数就是被代理的Mapper接口
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        // 调用UserMapper对象的findUserById
        // User user = userMapper.findUserById(1);
        // 调用UserMapper对象的testLazyLoading
        List<OrdersExt> orderList = userMapper.testLazyLoading();
        if (orderList != null && orderList.size() > 0) {
            for (OrdersExt ext : orderList) {
                System.out.println(ext);
            }
        }
        // 释放资源
        sqlSession.close();
    }

    // 测试一级缓存
    @Test
    public void testOneLevelCache() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        // 第一次查询ID为1的用户，去缓存找，找不到就去查找数据库
        User user1 = mapper.findUserById(1);
        System.out.println(user1);

        User user = new User();
        user.setUsername("隔壁老詹1");
        user.setAddress("洛杉矶湖人");
        // 执行增删改操作，清空缓存
        // mapper.insertUser(user);

        // 第二次查询ID为1的用户
        User user2 = mapper.findUserById(1);
        System.out.println(user2);

        sqlSession.close();
    }

    // 测试二级缓存
    @Test
    public void testTwoLevelCache() throws Exception {
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();

        UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);
        UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
        // 第一次查询ID为1的用户，去缓存找，找不到就去查找数据库
        User user1 = mapper1.findUserById(1);
        System.out.println(user1);

        // 关闭SqlSession1
        sqlSession1.close();

        // 第二次查询ID为1的用户
        User user2 = mapper2.findUserById(1);
        System.out.println(user2);

        // 关闭SqlSession2
        sqlSession2.close();
    }

}
