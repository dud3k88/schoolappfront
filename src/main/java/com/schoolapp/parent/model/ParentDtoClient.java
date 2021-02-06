package com.schoolapp.parent.model;

import com.schoolapp.child.model.ChildDtoClient;

import java.util.ArrayList;
import java.util.List;

public class ParentDtoClient {
    private String id;
    private String firstName;
    private String secondName;
    private String emailAddress;

    public ParentDtoClient() {
    }

    public ParentDtoClient(String id, String firstName, String secondName, String emailAddress) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.emailAddress = emailAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return getEmailAddress();
    }
}
