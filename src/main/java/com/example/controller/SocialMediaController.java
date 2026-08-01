package com.example.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;

import org.springframework.web.bind.annotation.*;
//import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;

//import java.net.UnknownServiceException;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import com.example.repository.*;
import com.example.service.*;
import java.util.List;

//TODO: self, can I integrate "natural" exceptions in my code?

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {
  //TODO: self, this the right way to integrate service?
  AccountService acctService;
  MessageService msgService;
  @Autowired
  public SocialMediaController(AccountService acctService, MessageService msgService) {
    this.acctService = acctService;
    this.msgService = msgService;
  }


  //AccountRepository acctRepo = appContext.getBean(AccountRepository.class);

  @PostMapping("/register")
  public Account foobarRegister(@RequestBody Account input) {
    if (input.getUsername().length() > 0 && input.getPassword().length() >= 4) {
      if (!acctService.usernameExists(input.getUsername())) {
        //return ResponseEntity.status(HttpStatus.OK).body(acctService.addAccount(input));
        return acctService.addAccount(input);
      } else {
        throw new ArithmeticException(); //409
        //return ResponseEntity.
      }
    }
    throw new ArrayStoreException(); //400
    //return null;
  }

  @PostMapping("/login")
  public Account foobarLogin(@RequestBody Account input) {
    List<Account> answer = acctService.usernameAndPassword(input.getUsername(), input.getPassword());
    /*
      try{
        return acctService.usernameAndPassword(input.getUsername(), input.getPassword());
      } catch (Exception e) {
      throw new NoSuchElementException(); //401
    }
      */
    if (answer.size() > 0) {
      return answer.get(0);
    } else {
      throw new NoSuchElementException(); //401
    }

  }

  @PostMapping("/messages")
  public Message foobarMsg(@RequestBody Message input) {
    if (input.getMessageText().length() > 0 && input.getMessageText().length() <= 255 && acctService.idExists(input.getPostedBy())) {
      return msgService.addMsg(input);
    } else {
      throw new ArrayStoreException(); //400
    }
  }

  @GetMapping("/messages")
  public List<Message> fooGetMsgs() {
    return msgService.getAllMsgs();
  }

  @GetMapping("/messages/{message_id}")
  public Message fooGetMsg(@PathVariable int message_id) {
    if (msgService.getMsgById(message_id).isPresent()) {
      return msgService.getMsgById(message_id).get();
    } else {
      return null;
    }
  }

  //TODO: self, this didn't need responseentity it seems
  @DeleteMapping("/messages/{message_id}")
  public ResponseEntity<Integer> fooDel(@PathVariable int message_id) {
    //ResponseEntity<Integer> result = ResponseEntity.status(200);
    if (msgService.getMsgById(message_id).isPresent()) {
      msgService.deleteById(message_id);
      return ResponseEntity.ok(1);
    } else {
      throw new ClassCastException(); //200
    }
  }

  //TODO: self, for the life of me I can't figure out why I'm failing that one test
  @PatchMapping("messages/{message_id}")
  public int fooUpdate(@PathVariable int message_id, @RequestBody String messageText) {
    /*
    if ((msgService.getMsgById(message_id).isPresent()) && (message_text.length() == 0) && (message_text.length() <= 255)) {
      msgService.updateById(message_id, message_text);
      return 1; //TODO: number of rows updated not implemented "right"
    } 
      */
    //if (msgService.getMsgById(message_id).isPresent() && (!message_text.isBlank())) {
    //if (msgService.idExists(message_id) && (messageText.length() <= 255) && (messageText.length() > 0)) {
    if (true) {
      msgService.updateById(message_id, messageText);
      return 1;
    } else {
      throw new ArrayStoreException(); //400
    }
  }

  @GetMapping("accounts/{account_id}/messages")
  public List<Message> fooUser(@PathVariable int account_id) {
    return msgService.msgByAcc(account_id);
  }

  @ExceptionHandler(ArithmeticException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public String foobarError409() {
    return "";
  }

  @ExceptionHandler(ArrayStoreException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String foobarError400() {
    return "";
  }

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public String foobarError401() {
    return "";
  }
  
  @ExceptionHandler(ClassCastException.class)
  @ResponseStatus(HttpStatus.OK)
  public void foobarNoError() {
    return;
  }
}