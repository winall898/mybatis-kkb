package com.kkb.mybatis.test;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.kkb.mybatis.mapper.AccountMapper;
import com.kkb.mybatis.po.Account;
import com.kkb.mybatis.po.AccountExample;
import com.kkb.mybatis.po.AccountExample.Criteria;

public class AccountMapperTest {
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
    public void testSelectByExample() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        AccountMapper mapper = sqlSession.getMapper(AccountMapper.class);

        // 动态封装查询条件、排序条件
        AccountExample example = new AccountExample();
        Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(1);
        List<Account> list = mapper.selectByExample(example);

        System.out.println(list);
    }

}
