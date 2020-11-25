package com.voronkov.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.voronkov.blog.dao.UserDao;
import com.voronkov.blog.model.User;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

  @Autowired
  private UserDao userDao;

  @GetMapping
  public String userList(Model model) {

    List<User> users = userDao.getAll();

    model.addAttribute("users", users);

    return "userlist";
  }

  @GetMapping("/{id}")
  public String userEditForm(@PathVariable("id") int id, Model model) {
    User user = userDao.findById(id);

    model.addAttribute("user", user);
    model.addAttribute("roles", Role.values());
    model.addAttribute("title", "Users data");

    return "useredit";
  }

  @PostMapping
  public String saveUser(@ModelAttribute("id") User user) {

    userDao.update(user);

    return "redirect:/user";
  }
}
