package com.kkb.mybatis.po;

import java.io.Serializable;
import java.util.Date;

// 注意事项：该POJO类一般由逆向工程生成，每次生成都会覆盖，所有扩展的字段最好写在扩展的继承类中
public class User implements Serializable {

    private static final long serialVersionUID = -551357521270040083L;

    private int id;
    private String username;
    private Date birthday;
    private String sex;
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", birthday=" + birthday + ", sex="
                + sex + ", address=" + address + "]";
    }


}
