package com.kkb.mybatis.test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.kkb.mybatis.po.QueryVO;
import com.kkb.mybatis.po.User;

public class UserDemoTest {

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

        // 设计模式分三类23种：创建型（5）、结构型（7）、行为型（11）
    }

    @Test
    public void testSelect() {
        // 由SqlSessionFactory工厂去创建SqlSession（会话）
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 调用sqlsession接口，去实现数据库的增删改查操作
        // List<OrdersVO> ordersVO = sqlSession.selectList("test.testResultType");

        // List<OrdersExt> orders = sqlSession.selectList("test.testOneToOne");

        // List<UserExt> userList = sqlSession.selectList("test.testOneToMany");

        QueryVO queryVO = new QueryVO();
        User user = new User();
        user.setUsername("李四");
        user.setSex("1");
        queryVO.setUser(user);

        List<Integer> idList = new ArrayList<Integer>();
        idList.add(28);
        idList.add(29);
        queryVO.setIds(idList);
        // List<User> users = sqlSession.selectList("test.findUserList2", queryVO);

        List<User> userList = sqlSession.selectList("test.findUserList3", idList);
        System.out.println(userList);
        // 释放资源
        sqlSession.close();
    }
}
