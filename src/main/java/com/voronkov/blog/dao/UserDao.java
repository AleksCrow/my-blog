package com.voronkov.blog.dao;

import com.voronkov.blog.model.User;

public interface UserDao {
  User findByUserName(String name);

  User addUser(User user);
}
