package com.voronkov.blog.dao;

import java.util.List;

import com.voronkov.blog.model.User;

public interface UserDao {

  User findByUserName(String username);

  User findById(int id);

  List<User> getAll();

  User addUser(User user);

  User update(User user);
}
