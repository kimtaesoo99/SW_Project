package com.example.sheetmusiclist.exception;

public class MemberEmailAlreadyExistsException extends RuntimeException{
    public MemberEmailAlreadyExistsException(String message) {
        super(message);
    }
}