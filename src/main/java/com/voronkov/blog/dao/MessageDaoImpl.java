package com.voronkov.blog.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.voronkov.blog.dao.mapper.MessageMapper;
import com.voronkov.blog.model.Message;
import com.voronkov.blog.model.User;

@Repository
@Transactional(readOnly = true)
public class MessageDaoImpl implements MessageDao {

  private final JdbcTemplate jdbcTemplate;

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  private final SimpleJdbcInsert insertMessage;

  @Autowired
  public MessageDaoImpl(JdbcTemplate jdbcTemplate,
      NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    this.insertMessage =
        new SimpleJdbcInsert(jdbcTemplate).withTableName("messages").usingGeneratedKeyColumns("id");
  }

  @Override
  @Transactional
  public Message getOne(int id) {
    List<Message> messages =
        jdbcTemplate.query("SELECT * FROM messages m, users u WHERE m.id = ? and u.id = m.user_id",
            new MessageMapper(), id);
    return DataAccessUtils.singleResult(messages);
  }

  @Override
  @Transactional
  public List<Message> getAllMessages() {
    return jdbcTemplate.query("SELECT * FROM messages m, users u WHERE u.id = m.user_id",
        new MessageMapper());
  }

  @Override
  @Transactional
  public Message add(Message message, User user) {
    MapSqlParameterSource map = new MapSqlParameterSource().addValue("id", message.getId())
        .addValue("text", message.getText()).addValue("tag", message.getTag())
        .addValue("user_id", user.getId()).addValue("filename", message.getFilename());

    Number newId = insertMessage.executeAndReturnKey(map);
    message.setId(newId.intValue());
    return message;
  }

  @Override
  @Transactional
  public List<Message> findByTag(String tag) {
    List<Message> messages =
        jdbcTemplate.query("SELECT * FROM messages m, users u WHERE tag = ? AND u.id = m.user_id",
            new MessageMapper(), tag);
    return messages;
  }
}
