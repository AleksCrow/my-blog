package com.voronkov.blog.dao;

import java.util.List;

import com.voronkov.blog.model.Message;

public interface MessageDao {

  Message getOne(int id);

  List<Message> getAllMessages();

  Message add(Message message);

  List<Message> findByTag(String tag);
}
