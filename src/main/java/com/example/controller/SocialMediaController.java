package com.example.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.UnknownServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
//import com.example.repository.*;
import com.example.service.*;

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
  @Autowired
  public SocialMediaController(AccountService acctService) {
    this.acctService = acctService;
  }


  //AccountRepository acctRepo = appContext.getBean(AccountRepository.class);

  @PostMapping("/register")
  public ResponseEntity<Account> foobarRegister(@RequestBody Account input) {
    if (input.getUsername().length() > 0 && input.getPassword().length() >= 4) {
      if (!acctService.usernameExists(input.getUsername())) {
        return ResponseEntity.status(HttpStatus.OK).body(acctService.addAccount(input));
      } else {
        throw new ArithmeticException(); //409
        //return ResponseEntity.
      }
    }
    throw new ArrayStoreException(); //400
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

}
