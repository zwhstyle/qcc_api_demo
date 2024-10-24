package com.org.qcc.dataapi.common.encryp;

public interface EncryptionStrategy {

  String encrypt(String content, String key) throws Exception;
}