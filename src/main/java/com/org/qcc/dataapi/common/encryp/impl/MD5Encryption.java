package com.org.qcc.dataapi.common.encryp.impl;

import com.org.qcc.dataapi.common.encryp.EncryptionAlgorithm;
import com.org.qcc.dataapi.common.encryp.EncryptionStrategy;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encryption implements EncryptionStrategy {

  @Override
  public String encrypt(String content, String key) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance(EncryptionAlgorithm.MD5.name());
    byte[] digest = md.digest(content.getBytes());
    return bytesToHex(digest);
  }

  private String bytesToHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
      sb.append(String.format("%02x", b));
    }
    return sb.toString();
  }
}
