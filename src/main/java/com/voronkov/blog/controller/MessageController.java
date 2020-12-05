package com.voronkov.blog.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.voronkov.blog.dao.MessageDao;
import com.voronkov.blog.model.Message;
import com.voronkov.blog.model.User;
import com.voronkov.blog.model.dto.MessageDto;
import com.voronkov.blog.util.FileUtil;
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
      @ModelAttribute @Valid MessageDto messageDto, BindingResult result) throws IOException {

    if (result.hasErrors()) {
      return "main";
    }

    Message message = new Message();

    FileUtil.fileMessageHandling(messageDto, message, uploadPath);

    MessageUtil.updateFromMessageDto(message, messageDto);

    messageDao.add(message, user);

    return "redirect:/main";
  }


}
