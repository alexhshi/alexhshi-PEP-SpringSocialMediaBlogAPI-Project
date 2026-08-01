package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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
  public Optional<Message> getMsgById(int id) {
    return msgRepo.findById(id);
  }
  public void deleteById(int id) {
    msgRepo.deleteById(id);
  }
  public void updateById(int id, String msg) {
    if (msgRepo.findById(id).isPresent()) {
      msgRepo.getById(id).setMessageText(msg);
    }
  }
  public List<Message> msgByAcc(int accId) {
    return msgRepo.findMessagesByPostedBy(accId);
  }
}
