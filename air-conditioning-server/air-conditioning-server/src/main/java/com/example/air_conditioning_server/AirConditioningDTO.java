package com.example.air_conditioning_server;

import com.example.air_conditioning_server.enums.Temperature;

public class AirConditioningDTO {
    private Boolean status;
    private Temperature temperature;

    public AirConditioningDTO() {}

    public AirConditioningDTO(Boolean status, Temperature temperature) {
        this.status = status;
        this.temperature = temperature;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }
}
