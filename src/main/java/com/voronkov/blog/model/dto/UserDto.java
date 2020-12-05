package com.voronkov.blog.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDto {

  private int id;

  @NotBlank(message = "User name cannot be empty")
  @Size(min = 3, max = 30, message = "Name should be between 2 and 30")
  private String username;

  @NotBlank(message = "Password cannot be empty")
  @Size(min = 6, max = 32, message = "Password should be between 6 and 32")
  private String password;

  @NotBlank(message = "Passwords must match")
  private String password2;

  @Email(message = "Email is not correct")
  @NotBlank(message = "Email cannot be empty")
  private String email;

  public UserDto(int id, String username, String password, String email) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
  }

  public UserDto() {}

  private void checkPassword() {
    if (!this.password.equals(password2)) {
      this.password2 = null;
    }
  }

  public int getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
    checkPassword();
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword2() {
    return password2;
  }

  public void setPassword2(String password2) {
    this.password2 = password2;
    checkPassword();
  }
}
