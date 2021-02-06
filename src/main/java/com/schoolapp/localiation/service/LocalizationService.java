package com.schoolapp.localiation.service;

import com.schoolapp.localiation.model.LocalizationDtoClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class LocalizationService {

    public List<LocalizationDtoClient> getAllLocalization() {
        RestTemplate restTemplate = new RestTemplate();
        LocalizationDtoClient[] localizationDtoClient = restTemplate.getForObject(
                "https://scoolapps.herokuapp.com/school/localization/getLocalizations",
                LocalizationDtoClient[].class);
        if (localizationDtoClient != null) {
            return Arrays.asList(localizationDtoClient);
        }
        return new ArrayList<>();
    }

    public LocalizationDtoClient getLocalization(Long localizationId) {
        RestTemplate restTemplate = new RestTemplate();
        LocalizationDtoClient localizationDtoClient = restTemplate.getForObject(
                "https://scoolapps.herokuapp.com/school/localization/getLocalization?localizationId=" + localizationId,
                LocalizationDtoClient.class);
        return localizationDtoClient;
    }

    public LocalizationDtoClient createLocalization (LocalizationDtoClient localizationDtoClient) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://scoolapps.herokuapp.com/school/localization/createLocalization";
        return restTemplate.postForObject(url, localizationDtoClient, LocalizationDtoClient.class);
    }

    public void deleteLocalization (Long localizationId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://scoolapps.herokuapp.com/school/localization/deleteLocalization?localizationId=" + localizationId;
        restTemplate.delete(url);
    }
}
