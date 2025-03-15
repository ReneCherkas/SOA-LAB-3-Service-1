package ru.soa_3.first_service.exception;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ErrorIQP")
public class ErrorIQP {
    private String message;

    public ErrorIQP() {}

    public ErrorIQP(String message) {
        this.message = message;
    }

    // Геттер и сеттер для message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
