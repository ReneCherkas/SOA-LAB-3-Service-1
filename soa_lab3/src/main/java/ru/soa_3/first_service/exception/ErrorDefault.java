package ru.soa_3.first_service.exception;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ErrorDefault")
public class ErrorDefault {
    private String message;

    public ErrorDefault() {}

    public ErrorDefault(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
