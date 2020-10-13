package com.project.wallet.apis;

import com.project.wallet.model.WalletUser;
import com.project.wallet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/sign-in")
  public ResponseEntity<?> userSignIn(@PathVariable("username") String username,
                                      @PathVariable("password") String password){
    //here we can use session after sign in
    return ResponseEntity.ok().build();
  }

  @PostMapping("/sign-up")
  public ResponseEntity<?> userSignUp(@RequestBody WalletUser walletUser){
    userService.createUser(walletUser);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
