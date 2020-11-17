package com.voronkov.blog.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.voronkov.blog.dao.UserDao;
import com.voronkov.blog.model.Role;
import com.voronkov.blog.model.User;

@Controller
public class UserController {

  @Autowired
  private UserDao userDao;

  @GetMapping("/registration")
  public String registration(Model model) {

    model.addAttribute("user", new User());

    return "registration";
  }

  @PostMapping("/registration")
  public String addUser(@ModelAttribute User user, Model model) {
    User userFromDb = userDao.findByUserName(user.getUsername());

    if (userFromDb != null) {
      model.addAttribute("message", "user already exists!");
      return "registration";
    }

    user.setActive(true);
    user.setRoles(Collections.singleton(Role.USER));

    userDao.addUser(user);
    return "redirect:/login";
  }
}
