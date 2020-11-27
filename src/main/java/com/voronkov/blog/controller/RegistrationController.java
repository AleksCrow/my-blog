package com.voronkov.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.voronkov.blog.model.User;
import com.voronkov.blog.service.UserService;

@Controller
public class RegistrationController {

  @Autowired
  private UserService userService;

  @GetMapping("/registration")
  public String registration(Model model) {

    model.addAttribute("user", new User());
    model.addAttribute("title", "Registration");

    return "registration";
  }

  @PostMapping("/registration")
  public String addUser(@ModelAttribute User user, Model model) {
    if (user.getUsername().equals("") || user.getPassword().equals("")) {
      return "redirect:/registration?errorEmpty";
    }

    if (!userService.addUser(user)) {
      return "redirect:/registration?errorExist";
    }
    model.addAttribute("message",
        "Activation code has been sent to you email. Please, activate you account!");

    return "registration";
  }

  @GetMapping("/activate/{code}")
  public String activate(Model model, @PathVariable String code) {
    boolean isActivated = userService.activateUser(code);

    if (isActivated) {
      model.addAttribute("message", "User successfully activated!");
    } else {
      model.addAttribute("message", "You account is already activated!");
    }

    return "login";
  }
}
