package com.company.exceptions;

public class FileWithIpsNotFoundException extends Exception{
    public FileWithIpsNotFoundException() {
        super();
    }

    public FileWithIpsNotFoundException(String message) {
        super(message);
    }
}
