package com.schoolapp.weather.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherDto {
    private float temperature;
    private int pressure;
    private int humidity;
    private float windSpeed;
}
