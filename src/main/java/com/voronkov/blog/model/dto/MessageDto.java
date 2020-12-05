package com.voronkov.blog.model.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.voronkov.blog.model.User;

public class MessageDto {

  private int id;

  @NotBlank(message = "Message cannot be empty")
  @Length(max = 2048, message = "Message too long")
  private String text;

  @NotBlank(message = "Tag cannot be empty")
  @Length(max = 20, message = "Tag too long")
  private String tag;
  private MultipartFile file;

  public MessageDto() {}

  public MessageDto(int id, String text, String tag, MultipartFile file) {
    this.id = id;
    this.text = text;
    this.tag = tag;
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

  public void setFile(MultipartFile file) {
    this.file = file;
  }
}
