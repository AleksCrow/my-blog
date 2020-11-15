package com.voronkov.blog.dao;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.voronkov.blog.model.Message;

@Repository
@Transactional(readOnly = true)
public class MessageDaoImpl implements MessageDao {

  private static final RowMapper<Message> ROW_MAPPER =
      BeanPropertyRowMapper.newInstance(Message.class);

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
        jdbcTemplate.query("SELECT * FROM messages WHERE id = ?", ROW_MAPPER, id);
    return DataAccessUtils.singleResult(messages);
  }

  @Override
  public List<Message> getAllMessages() {
    return jdbcTemplate.query("SELECT * FROM messages", ROW_MAPPER);
  }

  @Override
  @Transactional
  public Message add(Message message) {
    MapSqlParameterSource map = new MapSqlParameterSource().addValue("id", message.getId())
        .addValue("text", message.getText()).addValue("tag", message.getTag());

    Number newId = insertMessage.executeAndReturnKey(map);
    message.setId(newId.intValue());
    return message;
  }

  @Override
  public List<Message> findByTag(String tag) {
    List<Message> messages =
        jdbcTemplate.query("SELECT * FROM messages WHERE tag = ?", ROW_MAPPER, tag);
    return messages;
  }

  // if (namedParameterJdbcTemplate.update("UPDATE messages SET text=:text, tag=:tag WHERE id=:id",
  // map) == 0) {
  // return null;
}
