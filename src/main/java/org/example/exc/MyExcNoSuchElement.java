package org.example.exc;

public class MyExcNoSuchElement extends RuntimeException {
    public MyExcNoSuchElement(String message) {
        super(message);
    }
}
