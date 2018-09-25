package com.kkb.mybatis.test;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.kkb.mybatis.anno.AnnotationUserMapper;
import com.kkb.mybatis.po.OrdersExt;
import com.kkb.mybatis.po.QueryVO;
import com.kkb.mybatis.po.User;
import com.kkb.mybatis.po.UserExt;

public class AnnotationUserMapperTest {

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
    public void testFindUserById() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AnnotationUserMapper userMapper = sqlSession.getMapper(AnnotationUserMapper.class);

        User user = userMapper.findUserById(1);
        System.out.println(user);
    }

    @Test
    public void testFindUserList() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AnnotationUserMapper userMapper = sqlSession.getMapper(AnnotationUserMapper.class);

        List<User> list = userMapper.findUserList("小明");
        System.out.println(list);
    }

    @Test
    public void testInsertUser() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AnnotationUserMapper userMapper = sqlSession.getMapper(AnnotationUserMapper.class);

        User user = new User();
        user.setUsername("开课吧-2");
        user.setSex("1");
        user.setAddress("致真大厦");
        userMapper.insertUser(user);
        System.out.println(user.getId());
    }

    @Test
    public void testDynamicSQL() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AnnotationUserMapper userMapper = sqlSession.getMapper(AnnotationUserMapper.class);

        QueryVO vo = new QueryVO();
        User user = new User();
        // user.setUsername("小明");
        // user.setSex("1");
        vo.setUser(user);
        List<User> list = userMapper.dynamicSQL(vo);
        System.out.println(list);
    }

    @Test
    public void testFindUserByIdWithResultMap() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AnnotationUserMapper userMapper = sqlSession.getMapper(AnnotationUserMapper.class);

        User user = userMapper.findUserByIdWithResultMap(1);

        System.out.println(user);
    }

    @Test
    public void testOne2One() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AnnotationUserMapper userMapper = sqlSession.getMapper(AnnotationUserMapper.class);

        List<OrdersExt> list = userMapper.one2One();
        System.out.println(list);
    }

    @Test
    public void testOne2Many() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AnnotationUserMapper userMapper = sqlSession.getMapper(AnnotationUserMapper.class);

        List<UserExt> list = userMapper.one2Many();

        System.out.println(list);
    }

    @Test
    public void testLazyLoading() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AnnotationUserMapper userMapper = sqlSession.getMapper(AnnotationUserMapper.class);

        // 此处代码如果查询用户信息的话，那么这是[直接加载]
        List<OrdersExt> list = userMapper.lazyLoading();

        for (OrdersExt ordersExt : list) {
            // 此处代码如果查询用户信息的话，那么这是[侵入式延迟加载]
            System.out.println(ordersExt.getId());

            // 此处代码如果查询用户信息的话，那么这是[深度延迟加载]
            System.out.println(ordersExt.getUser().getId());
        }
    }

}
