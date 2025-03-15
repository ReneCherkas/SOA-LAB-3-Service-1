package ru.soa_3.first_service.exception;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ErrorISE")
public class ErrorISE {
    private String message;

    public ErrorISE() {}

    public ErrorISE(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}