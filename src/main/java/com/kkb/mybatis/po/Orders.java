package com.kkb.mybatis.po;

import java.util.Date;

// 注意事项：该POJO类一般由逆向工程生成，每次生成都会覆盖，所有扩展的字段最好写在扩展的继承类中
public class Orders {
    private int id;
    private int userId;
    private String number;
    private Date createtime;
    private String note;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
