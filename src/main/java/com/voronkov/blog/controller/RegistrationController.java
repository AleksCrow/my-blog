package com.voronkov.blog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.voronkov.blog.model.User;
import com.voronkov.blog.model.dto.UserDto;
import com.voronkov.blog.service.UserService;
import com.voronkov.blog.util.UserUtil;

@Controller
public class RegistrationController {

  @Autowired
  private UserService userService;

  @GetMapping("/registration")
  public String registration(Model model) {

    model.addAttribute("user", new User());
    model.addAttribute("userDto", new UserDto());
    model.addAttribute("title", "Registration");

    return "registration";
  }

  @PostMapping("/registration")
  public String addUser(@ModelAttribute @Valid UserDto userDto, BindingResult result, Model model) {
    if (result.hasErrors() || !userDto.getPassword().equals(userDto.getPassword2())) {
      return "registration";
    }

    if (!userService.addUser(UserUtil.updateFromUserDto(new User(), userDto))) {
      return "registration";
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

    return "profile";
  }
}
