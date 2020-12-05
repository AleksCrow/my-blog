package com.voronkov.blog.model;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class Message {

  private int id;

  @NotBlank(message = "Please fill the message")
  @Length(max = 2048, message = "Message too long")
  private String text;
  private String tag;
  private User user;
  private String filename;

  public Message(String text, String tag, User user) {
    this.user = user;
    this.text = text;
    this.tag = tag;
  }

  public Message() {

  }

  public String getUsername() {
    return user != null ? user.getUsername() : "<none>";
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

  public void setId(int id) {
    this.id = id;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }
}
