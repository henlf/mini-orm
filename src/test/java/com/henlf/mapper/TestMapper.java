package com.henlf.mapper;

import com.henlf.orm.User;
import com.henlf.orm.annotation.Select;
import com.henlf.orm.annotation.Update;

import java.util.List;

public interface TestMapper {
    @Select("select * from test where id = ?")
    User selectById(String id);

    @Select("select * from test")
    List<User> selectAll();

    @Update("update test set name = ? where id = ?")
    void update(User user);
}
