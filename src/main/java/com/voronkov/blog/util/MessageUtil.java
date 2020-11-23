package com.voronkov.blog.util;

import com.voronkov.blog.model.Message;
import com.voronkov.blog.model.dto.MessageDto;

public class MessageUtil {

  public static Message updateFromMessageDto(Message message, MessageDto messageDto) {
    message.setId(messageDto.getId());
    message.setText(messageDto.getText());
    message.setTag(messageDto.getTag());
    message.setUser(messageDto.getUser());

    return message;
  }
}
