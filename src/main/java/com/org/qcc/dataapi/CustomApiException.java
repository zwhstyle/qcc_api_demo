package com.org.qcc.dataapi;

public class CustomApiException extends RuntimeException {

  private final int statusCode;

  public CustomApiException(int statusCode, String message) {
    super(message);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}