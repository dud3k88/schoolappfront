package com.schoolapp.euro.service;

import com.schoolapp.euro.model.EuroValue;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class EuroService {

    private RestTemplate restTemplate = new RestTemplate();

    public EuroValue getEuroValue() {
        String url = "https://scoolapps.herokuapp.com/school/euro/get";

        return restTemplate.getForObject(url, EuroValue.class);
    }
}
