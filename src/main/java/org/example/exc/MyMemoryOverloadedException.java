package org.example.exc;

public class MyMemoryOverloadedException extends RuntimeException {
    public MyMemoryOverloadedException(String message) {
        super(message);
    }
}
