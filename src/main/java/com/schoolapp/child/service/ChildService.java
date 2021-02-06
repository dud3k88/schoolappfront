package com.schoolapp.child.service;

import com.schoolapp.child.model.ChildDtoClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ChildService {

    public List<ChildDtoClient> getAllChild() {
        RestTemplate restTemplate = new RestTemplate();
        ChildDtoClient[] childDtoClientList = restTemplate.getForObject(
                "https://scoolapps.herokuapp.com/school/children/getChildren",
                ChildDtoClient[].class);
        if (childDtoClientList != null) {
            return Arrays.asList(childDtoClientList);
        }
        return new ArrayList<>();
    }

    public ChildDtoClient createChild(ChildDtoClient childDtoClient) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://scoolapps.herokuapp.com/school/children/createChild";
        return restTemplate.postForObject(url, childDtoClient, ChildDtoClient.class);
    }

    public void deleteChild(Long childId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://scoolapps.herokuapp.com/school/children/deleteChild?childId=" + childId;
        restTemplate.delete(url);
    }
}
