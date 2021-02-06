package com.schoolapp.parent.service;

import com.schoolapp.parent.model.ParentDtoClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ParentService {

    public List<ParentDtoClient> getParents() {
        RestTemplate restTemplate = new RestTemplate();
        ParentDtoClient[] parentDtoClients = restTemplate.getForObject(
                "https://scoolapps.herokuapp.com/school/parents/getParents",
                ParentDtoClient[].class);
        if (parentDtoClients != null) {
            return Arrays.asList(parentDtoClients);
        }
        return new ArrayList<>();
    }

    public ParentDtoClient getParent(Long parentId) {
        RestTemplate restTemplate = new RestTemplate();
        ParentDtoClient parentDtoClient = restTemplate.getForObject(
                "https://scoolapps.herokuapp.com/school/parents/getParent?parentId=" + parentId,
                ParentDtoClient.class);
        return parentDtoClient;
    }

    public ParentDtoClient createParent(ParentDtoClient parentDtoClient) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://scoolapps.herokuapp.com/school/parents/createParent";
        return restTemplate.postForObject(url, parentDtoClient, ParentDtoClient.class);
    }

    public void deleteParent(Long parentId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://scoolapps.herokuapp.com/school/parents/deleteParent?parentId=" + parentId;
        restTemplate.delete(url);
    }
}
