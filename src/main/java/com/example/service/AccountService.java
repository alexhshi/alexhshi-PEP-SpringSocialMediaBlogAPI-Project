package com.example.service;

import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.*;

@Service
public class AccountService {
  AccountRepository acctRepo;
  @Autowired
  public AccountService(AccountRepository acctRepo) {
    this.acctRepo = acctRepo;
  }

  public boolean usernameExists(String username) {
    return acctRepo.findAccountsByUsername(username).size() > 0;
  }

  public Account addAccount(Account input) {
    return acctRepo.save(input);
  }

  public Account usernameAndPassword(String username, String password) {
    return acctRepo.findAccountByUsernameAndPassword(username, password);
  }
}
