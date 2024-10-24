package com.org.qcc.dataapi.common.encryp.impl;

import com.org.qcc.dataapi.common.encryp.EncryptionAlgorithm;
import com.org.qcc.dataapi.common.encryp.EncryptionStrategy;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DESEncryption implements EncryptionStrategy {

  @Override
  public String encrypt(String content, String key) throws Exception {
    SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), EncryptionAlgorithm.DES.name());
    Cipher cipher = Cipher.getInstance(EncryptionAlgorithm.DES.name());
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    byte[] encrypted = cipher.doFinal(content.getBytes());
    return Base64.getEncoder().encodeToString(encrypted);
  }
}
