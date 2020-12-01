package com.voronkov.blog.model.dto;

public class UserDto {

  private int id;
  private String username;
  private String password;
  private String email;

  public UserDto(int id, String username, String password, String email) {
    super();
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
  }

  public UserDto() {

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
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
