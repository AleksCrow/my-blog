package com.voronkov.blog.dao;

import com.voronkov.blog.model.User;

public interface UserDao {
  User findByUserName(String username);

  User addUser(User user);
}
