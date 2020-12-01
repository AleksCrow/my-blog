package com.voronkov.blog.service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.voronkov.blog.dao.UserDao;
import com.voronkov.blog.model.Role;
import com.voronkov.blog.model.User;
import com.voronkov.blog.model.dto.UserDto;
import com.voronkov.blog.util.UserUtil;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private UserDao userDao;

  @Autowired
  private EmailSender emailSender;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userDao.findByUserName(username);
  }

  public boolean addUser(User user) {
    User userFromDb = userDao.findByUserName(user.getUsername());

    if (userFromDb != null) {
      return false;
    }

    user.setActive(true);
    user.setRoles(Collections.singleton(Role.USER));
    user.setActivationCode(UUID.randomUUID().toString());

    userDao.addUser(user);

    sendActivationMessage(user);

    return true;
  }

  public boolean activateUser(String code) {
    User user = userDao.findByActivationCode(code);

    if (user == null) {
      return false;
    }

    user.setActivationCode(null);
    userDao.update(user);

    return true;
  }

  public List<User> getAll() {
    return userDao.getAll();
  }

  public User findById(int id) {
    return userDao.findById(id);
  }

  public void update(User user) {
    userDao.update(user);
  }

  public void update(UserDto userDto) {
    User user = userDao.findById(userDto.getId());

    boolean ifEmailChanged = !user.getEmail().equals(userDto.getEmail());

    UserUtil.updateFromUserDto(user, userDto);

    if (ifEmailChanged) {
      user.setActivationCode(UUID.randomUUID().toString());
    }
    userDao.update(user);
    if (ifEmailChanged) {
      sendActivationMessage(user);
    }
  }

  private void sendActivationMessage(User user) {
    String message = String.format(
        "Hello, %s! \n" + "Welcome to My blog.\n"
            + "Please, visit next link: http://localhost:8080/activate/%s",
        user.getUsername(), user.getActivationCode());

    if (!org.springframework.util.ObjectUtils.isEmpty(user.getEmail())) {
      emailSender.send(user.getEmail(), "Activation code", message);
    }
  }
}
