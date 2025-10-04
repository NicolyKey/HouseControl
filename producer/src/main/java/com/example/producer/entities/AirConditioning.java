package com.example.producer.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class AirConditioning {
    private boolean status;
    @Getter
    private int temperatura;

    public AirConditioning(boolean status, int temperatura) {
        this.status = status;
        this.temperatura = temperatura;
    }

    public boolean getStatus() {
        return status;
    }

    public void increaseTemperature(){
        if(!this.status){
            throw new RuntimeException("Falha ao tentar aumentar a temperatura de um ar desligado");
        }
        setTemperatura(this.temperatura + 1);
    }

    public void decreaseTemperature(){
        if(!this.status){
            throw new RuntimeException("Falha ao tentar abaixar a temperatura de um ar desligado");
        }
        setTemperatura(this.temperatura - 1);
    }
}
