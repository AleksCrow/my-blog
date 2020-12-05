package com.voronkov.blog.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import com.voronkov.blog.model.Message;
import com.voronkov.blog.model.dto.MessageDto;

public class FileUtil {

  public static void fileMessageHandling(MessageDto messageDto, Message message, String uploadPath)
      throws IOException {
    if (messageDto.getFile() != null && !messageDto.getFile().getOriginalFilename().isEmpty()) {
      File uploadDir = new File(uploadPath);

      if (!uploadDir.exists()) {
        uploadDir.mkdir();
      }

      String uuidFile = UUID.randomUUID().toString();
      String resultFileName = uuidFile + "." + messageDto.getFile().getOriginalFilename();
      messageDto.getFile().transferTo(new File(uploadPath + "/" + resultFileName));

      message.setFilename(resultFileName);
    }
  }
}
