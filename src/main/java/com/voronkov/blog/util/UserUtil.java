package com.voronkov.blog.util;

import com.voronkov.blog.model.User;
import com.voronkov.blog.model.dto.UserDto;

public class UserUtil {

  public static User updateFromUserDto(User user, UserDto userDto) {
    user.setUsername(userDto.getUsername());
    if (!userDto.getPassword().isEmpty()) {
      user.setPassword(userDto.getPassword());
    }
    if (!userDto.getEmail().isEmpty()) {
      user.setEmail(userDto.getEmail());
    }

    return user;
  }
}
