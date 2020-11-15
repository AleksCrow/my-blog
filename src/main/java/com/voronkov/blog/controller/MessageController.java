package com.voronkov.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.voronkov.blog.dao.MessageDao;
import com.voronkov.blog.model.Message;

@Controller
public class MessageController {

  @Autowired
  private MessageDao messageDao;

  @GetMapping
  public String hello() {
    return "hello";
  }

  @GetMapping("/main")
  public String getMessages(
      @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
      Model model) {
    System.out.println(filter);

    Iterable<Message> messagesList;
    if (filter != null && !filter.isEmpty()) {
      messagesList = messageDao.findByTag(filter);
    } else {
      messagesList = messageDao.getAllMessages();
    }

    model.addAttribute("messages", messagesList);
    model.addAttribute("message", new Message());

    return "main";
  }

  @PostMapping("/main")
  public String addMessage(@ModelAttribute Message message) {

    messageDao.add(message);

    return "redirect:/main";
  }
}
