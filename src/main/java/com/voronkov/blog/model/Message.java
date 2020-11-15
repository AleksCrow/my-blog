package com.voronkov.blog.model;

public class Message {

  private int id;
  private String text;
  private String tag;

  public Message(String text, String tag) {
    this.text = text;
    this.tag = tag;
  }

  public Message() {

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

  @Override
  public String toString() {
    return "Message [id=" + id + ", text=" + text + ", tag=" + tag + "]";
  }
}
