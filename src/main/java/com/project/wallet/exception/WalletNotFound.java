package com.project.wallet.exception;

public class WalletNotFound extends RuntimeException {
  public WalletNotFound(String msg) {
    super(msg);
  }
}
