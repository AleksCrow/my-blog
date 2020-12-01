package com.voronkov.blog.util;

import com.voronkov.blog.model.User;
import com.voronkov.blog.model.dto.UserDto;

public class UserUtil {

  public static User updateFromUserDto(User user, UserDto userDto) {
    if (!userDto.getPassword().equals("")) {
      user.setPassword(userDto.getPassword());
    }
    if (!userDto.getEmail().equals("")) {
      user.setEmail(userDto.getEmail());
    }

    return user;
  }
}
