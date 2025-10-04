package com.example.air_conditioning_server;

public class AirConditioning {
    private boolean status;
    private int temperatura;

    public AirConditioning() {
    }

    public AirConditioning(boolean status, int temperatura) {
        this.status = status;
        this.temperatura = temperatura;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public void increaseTemperature(){
        setTemperatura(this.temperatura + 1);
    }

    public void decreaseTemperature(){
        setTemperatura(this.temperatura - 1);
    }
}
