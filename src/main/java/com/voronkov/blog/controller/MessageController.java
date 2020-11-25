package com.voronkov.blog.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.voronkov.blog.dao.MessageDao;
import com.voronkov.blog.model.Message;
import com.voronkov.blog.model.User;
import com.voronkov.blog.model.dto.MessageDto;
import com.voronkov.blog.util.MessageUtil;

@Controller
public class MessageController {

  @Autowired
  private MessageDao messageDao;

  @Value("${upload.path}")
  private String uploadPath;


  @GetMapping
  public String hello() {
    return "hello";
  }

  @GetMapping("/main")
  public String getMessages(
      @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
      Model model) {

    Iterable<Message> messagesList;
    if (filter != null && !filter.isEmpty()) {
      messagesList = messageDao.findByTag(filter);
    } else {
      messagesList = messageDao.getAllMessages();
    }

    model.addAttribute("messages", messagesList);
    model.addAttribute("messageDto", new MessageDto());
    model.addAttribute("title", "Messages");

    return "main";
  }

  @PostMapping("/main")
  public String addMessage(@AuthenticationPrincipal User user,
      @ModelAttribute MessageDto messageDto) throws IOException {

    Message message = new Message();

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

    MessageUtil.updateFromMessageDto(message, messageDto);

    messageDao.add(message, user);

    return "redirect:/main";
  }
}
