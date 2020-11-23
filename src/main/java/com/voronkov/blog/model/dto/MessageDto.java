package com.voronkov.blog.model.dto;

import org.springframework.web.multipart.MultipartFile;

import com.voronkov.blog.model.User;

public class MessageDto {

  private int id;
  private String text;
  private String tag;
  private User user;
  private MultipartFile file;

  public MessageDto() {}

  public MessageDto(int id, String text, String tag, User user, MultipartFile file) {
    this.id = id;
    this.text = text;
    this.tag = tag;
    this.user = user;
    this.file = file;
  }

  public int getId() {
    return id;
  }

  public String getText() {
    return text;
  }

  public String getTag() {
    return tag;
  }

  public User getUser() {
    return user;
  }

  public MultipartFile getFile() {
    return file;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setFile(MultipartFile file) {
    this.file = file;
  }
}
