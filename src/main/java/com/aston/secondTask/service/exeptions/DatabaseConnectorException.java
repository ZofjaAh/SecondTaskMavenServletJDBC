package com.aston.secondTask.service.exeptions;

public class DatabaseConnectorException extends RuntimeException {

  public DatabaseConnectorException(Exception ex) {
    super(ex);
  }
}