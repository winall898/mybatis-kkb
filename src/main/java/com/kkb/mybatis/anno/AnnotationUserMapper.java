package com.kkb.mybatis.anno;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.FetchType;

import com.kkb.mybatis.po.OrdersExt;
import com.kkb.mybatis.po.QueryVO;
import com.kkb.mybatis.po.User;
import com.kkb.mybatis.po.UserExt;

public interface AnnotationUserMapper {
    // 查询
    @Select("SELECT * FROM user WHERE id = #{id}")
    public User findUserById(int id);

    // 模糊查询用户列表
    @Select("SELECT * FROM user WHERE username LIKE '%${value}%'")
    public List<User> findUserList(String username);

    // 添加并实现主键返回
    @Insert("INSERT INTO user (username,birthday,sex,address) VALUES (#{username},#{birthday},#{sex},#{address})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", resultType = int.class,
            before = false)
    public void insertUser(User user);

    // 动态SQL
    @SelectProvider(type = UserSqlBuilder.class, method = "getDynamicSQL")
    public List<User> dynamicSQL(QueryVO vo);

    // 动态SQL需要的类
    class UserSqlBuilder {
        // 注意事项：动态SQL方法参数必须一致（获取动态sQL的方法的参数和mapper接口的方法参数必须一致）
        public String getDynamicSQL(final QueryVO vo) {
            // SQL对象：让我们获取动态sQL语句
            return new SQL() {
                {
                    SELECT("*");
                    FROM("user");
                    User user = vo.getUser();
                    if (user != null) {
                        if (user.getUsername() != null && !user.getUsername().equals("")) {
                            WHERE("username like '%" + user.getUsername() + "%'");
                        }
                        if (user.getSex() != null && !user.getSex().equals("")) {
                            WHERE("sex =" + user.getSex());
                        }
                    }
                    ORDER_BY("id");
                }
            }.toString();
        }
    }

    // 使用Results注解完成结果映射
    @Results({@Result(column = "id", property = "id"),
            @Result(column = "username", property = "username"),
            @Result(column = "sex", property = "sex"),
            @Result(column = "address", property = "address")})
    @Select("SELECT * FROM user WHERE id = #{id}")
    public User findUserByIdWithResultMap(int id);

    // 演示一对一
    public List<OrdersExt> one2One();

    // 演示一对多
    public List<UserExt> one2Many();

    // 演示延迟加载
    @Results({@Result(column = "id", property = "id"),
            @Result(column = "user_id", property = "user_id"),
            @Result(column = "number", property = "number"),
            @Result(column = "note", property = "note"),
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "com.kkb.mybatis.anno.AnnotationUserMapper.findUserById",
                            fetchType = FetchType.LAZY))})
    @Select("SELECT * FROM orders")
    public List<OrdersExt> lazyLoading();

}
