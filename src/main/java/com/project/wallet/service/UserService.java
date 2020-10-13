package com.project.wallet.service;

import com.project.wallet.dao.UserRepository;
import com.project.wallet.model.WalletUser;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private WalletService walletService;

  public void createUser(WalletUser user) {
    String userId = UUID.randomUUID().toString();
    String walletId = walletService.createWallet();
    WalletUser newUser = WalletUser.builder()
        .userId(userId)
        .username(user.getUsername())
        .email(user.getEmail())
        .password(user.getPassword())
        .walletId(walletId)
        .build();
    userRepository.save(newUser);
  }
}
