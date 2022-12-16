package com.dydong.service;

import com.dydong.pojo.User;

import java.util.List;

public interface UserService {
    //add user
    public int addUser(User user);
    //query all
    public List<User> queryAllUser();
    //query by name
    public User queryUserByName(String username);
    //query by id
    public User queryUserById(int id);
    //update
    public void updateUser(User user);
    //delete User
    public int deleteUserById(int id);
}
