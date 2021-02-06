package com.schoolapp.weather.service;

import com.schoolapp.weather.model.WeatherDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    public WeatherDto getWeather() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://scoolapps.herokuapp.com/school/weather";
        return restTemplate.getForObject(url, WeatherDto.class);
    }
}
