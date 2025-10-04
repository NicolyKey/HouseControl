package com.example.producer.entities;

public class Light {

    private boolean status;

    public Light() {
    }

    public Light(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
