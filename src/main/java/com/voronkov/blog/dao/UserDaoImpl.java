package com.voronkov.blog.dao;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  public User findByUserName(String username) {
    List<User> users =
        jdbcTemplate.query("SELECT * FROM users WHERE username = ?", ROW_MAPPER, username);
    return setRoles(DataAccessUtils.singleResult(users));
  }

  @Override
  @Transactional
  public List<User> getAll() {
    Map<Integer, Set<Role>> map = new HashMap<>();
    jdbcTemplate.query("SELECT * FROM user_roles", rs -> {
      map.computeIfAbsent(rs.getInt("user_id"), userId -> EnumSet.noneOf(Role.class))
          .add(Role.valueOf(rs.getString("role")));
    });
    List<User> users = jdbcTemplate.query("SELECT * FROM users ORDER BY username", ROW_MAPPER);
    users.forEach(u -> u.setRoles(map.get(u.getId())));
    return users;
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

  @Override
  @Transactional
  public User findById(int id) {
    List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id = ?", ROW_MAPPER, id);
    return setRoles(DataAccessUtils.singleResult(users));
  }

  @Override
  @Transactional
  public User update(User user) {
    BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

    if (namedParameterJdbcTemplate.update("UPDATE users SET username=:username, " + "email=:email, "
        + "password=:password, " + "activation_code=:activationCode " + "WHERE id=:id",
        parameterSource) == 0) {
      return null;
    }
    deleteRoles(user);
    insertRoles(user);

    return user;
  }

  @Override
  @Transactional
  public User findByActivationCode(String code) {
    List<User> users =
        jdbcTemplate.query("SELECT * FROM users WHERE activation_code = ?", ROW_MAPPER, code);
    return setRoles(DataAccessUtils.singleResult(users));
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

  private void deleteRoles(User u) {
    jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", u.getId());
  }

  private User setRoles(User u) {
    if (u != null) {
      List<Role> roles = jdbcTemplate.queryForList("SELECT role FROM user_roles WHERE user_id=?",
          Role.class, u.getId());
      u.setRoles(roles);
    }
    return u;
  }

}
