package com.dydong.mapper;

import com.dydong.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    //add user
    public int addUser(User user);
    //query all
    public List<User> queryAllUser();
    //query by name
    public User queryUserByName(String username);
    //query by id
    public User queryUserById(int id);
    //update User
    public void updateUser(User user);
    //delete User
    public int deleteUserById(int id);
}
