package com.schoolapp.child.model;

import com.schoolapp.group.model.GroupDtoClient;
import com.schoolapp.parent.model.ParentDtoClient;

public class ChildDtoClient {
    private Long id;
    private String firstName;
    private String secondName;
    private int yearOfBirth;
    private ParentDtoClient parent;
    private GroupDtoClient group;

    public ChildDtoClient() {
    }

    public ChildDtoClient(String firstName, String secondName, int yearOfBirth, ParentDtoClient parent) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.yearOfBirth = yearOfBirth;
        this.parent = parent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public ParentDtoClient getParent() {
        return parent;
    }

    public void setParent(ParentDtoClient parent) {
        this.parent = parent;
    }

    public GroupDtoClient getGroup() {
        return group;
    }

    public void setGroup(GroupDtoClient group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "ChildDtoClient{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", parent=" + parent +
                ", group=" + group +
                '}';
    }
}
