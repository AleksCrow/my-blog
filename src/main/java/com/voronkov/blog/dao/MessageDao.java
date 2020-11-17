package com.voronkov.blog.dao;

import java.util.List;

import com.voronkov.blog.model.Message;
import com.voronkov.blog.model.User;

public interface MessageDao {

  Message getOne(int id);

  List<Message> getAllMessages();

  Message add(Message message, User user);

  List<Message> findByTag(String tag);
}
