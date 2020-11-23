package com.voronkov.blog.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.voronkov.blog.model.Message;
import com.voronkov.blog.model.User;

public class MessageMapper implements RowMapper<Message> {

  @Override
  public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
    Message message = new Message();
    message.setId(rs.getInt("id"));
    message.setText(rs.getString("text"));
    message.setTag(rs.getString("tag"));
    message.setFilename(rs.getString("filename"));

    User user = new User();
    user.setId(rs.getInt(6));
    user.setUsername(rs.getString("username"));
    user.setPassword(rs.getString("password"));
    user.setActive(rs.getBoolean("active"));

    message.setUser(user);

    return message;
  }

}
