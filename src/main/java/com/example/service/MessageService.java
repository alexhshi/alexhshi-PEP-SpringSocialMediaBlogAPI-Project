package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageService {
  MessageRepository msgRepo;
  @Autowired
  public MessageService(MessageRepository msgRepo) {
    this.msgRepo = msgRepo;
  }
  public Message addMsg(Message input) {
    return msgRepo.save(input);
  }
  public List<Message> getAllMsgs() {
    return msgRepo.findAll();
  }
  public Message getMsgById(int id) {
    return msgRepo.findById(id).get();
  }
}
