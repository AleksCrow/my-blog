package com.voronkov.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.voronkov.blog.model.User;
import com.voronkov.blog.model.dto.UserDto;
import com.voronkov.blog.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping
  public String userList(Model model) {

    List<User> users = userService.getAll();

    model.addAttribute("users", users);

    return "userlist";
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/{id}")
  public String usersEditForm(@PathVariable("id") int id, Model model) {
    User user = userService.findById(id);

    model.addAttribute("user", user);
    model.addAttribute("roles", Role.values());
    model.addAttribute("title", "Users data");

    return "useredit";
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping
  public String saveUser(@ModelAttribute("id") User user) {

    userService.update(user);

    return "redirect:/user";
  }

  @GetMapping("/profile")
  public String getProfile(Model model, @AuthenticationPrincipal User user) {
    if (!model.containsAttribute("userDto")) {
      model.addAttribute("userDto", new UserDto());
    }
    model.addAttribute("username", user.getUsername());
    model.addAttribute("email", user.getEmail());

    return "profile";
  }

  @PostMapping("/profile")
  public String updateProfile(@AuthenticationPrincipal User user,
      @ModelAttribute @Valid UserDto userDto, BindingResult result, Model model,
      final RedirectAttributes redirectAttributes) {

    if (result.hasErrors()) {
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userDto",
          result);
      redirectAttributes.addFlashAttribute("userDto", userDto);
      return "redirect:/user/profile";
    }

    userDto.setUsername(user.getUsername());
    userDto.setId(user.getId());

    userService.update(userDto);

    model.addAttribute("message", "User updated");

    return "redirect:/user/profile";
  }
}
