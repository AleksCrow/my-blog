package com.voronkov.blog.dao;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.voronkov.blog.model.Role;
import com.voronkov.blog.model.User;

@Repository
@Transactional(readOnly = true)
public class UserDaoImpl implements UserDao {

  private static final BeanPropertyRowMapper<User> ROW_MAPPER =
      BeanPropertyRowMapper.newInstance(User.class);

  private final JdbcTemplate jdbcTemplate;

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  private final SimpleJdbcInsert insertUser;

  @Autowired
  public UserDaoImpl(JdbcTemplate jdbcTemplate,
      NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    this.insertUser =
        new SimpleJdbcInsert(jdbcTemplate).withTableName("users").usingGeneratedKeyColumns("id");
  }

  @Override
  @Transactional
  public User findByUserName(String name) {
    List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE name = ?", ROW_MAPPER, name);
    return setRoles(DataAccessUtils.singleResult(users));
  }

  @Override
  @Transactional
  public User addUser(User user) {
    BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

    Number newKey = insertUser.executeAndReturnKey(parameterSource);
    user.setId(newKey.intValue());
    insertRoles(user);
    return user;
  }

  private void insertRoles(User u) {
    Set<Role> roles = u.getRoles();
    if (!CollectionUtils.isEmpty(roles)) {
      jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES (?, ?)", roles,
          roles.size(), (ps, role) -> {
            ps.setInt(1, u.getId());
            ps.setString(2, role.name());
          });
    }
  }

  private User setRoles(User u) {
    if (u != null) {
      List<Role> roles = jdbcTemplate.queryForList("SELECT role FROM user_roles  WHERE user_id=?",
          Role.class, u.getId());
      u.setRoles(roles);
    }
    return u;
  }

}
