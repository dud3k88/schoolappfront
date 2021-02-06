package com.schoolapp.group.service;

import com.schoolapp.group.model.GroupDtoClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GroupService {
    public List<GroupDtoClient> getAllGroups() {
        RestTemplate restTemplate = new RestTemplate();
        GroupDtoClient[] groupDtoClient = restTemplate.getForObject(
                "https://scoolapps.herokuapp.com/school/groups/getGroups",
                GroupDtoClient[].class);
        if (groupDtoClient != null) {
            return Arrays.asList(groupDtoClient);
        }
        return new ArrayList<>();
    }

    public GroupDtoClient getGroup(Long groupId) {
        RestTemplate restTemplate = new RestTemplate();
        GroupDtoClient groupDtoClient = restTemplate.getForObject(
                "https://scoolapps.herokuapp.com/school/groups/getGroup?groupId=" + groupId,
                GroupDtoClient.class);
        return groupDtoClient;
    }

    public GroupDtoClient createGroup(GroupDtoClient groupDtoClient) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://scoolapps.herokuapp.com/school/groups/createGroup";
        return restTemplate.postForObject(url, groupDtoClient, GroupDtoClient.class);
    }

    public void deleteGroup(Long groupId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://scoolapps.herokuapp.com/school/groups/deleteGroup?groupId=" + groupId;
        restTemplate.delete(url);
    }
}
