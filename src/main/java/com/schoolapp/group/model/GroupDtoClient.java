package com.schoolapp.group.model;

import com.schoolapp.localiation.model.LocalizationDtoClient;

public class GroupDtoClient {
    private Long id;
    private String groupName;
    private int minYearOfBirth;
    private int maxYearOfBirth;
    private LocalizationDtoClient localizationDtoClient;

    public GroupDtoClient() {
    }

    public GroupDtoClient(String groupName, int minYearOfBirth, int maxYearOfBirth, LocalizationDtoClient localizationDtoClient) {
        this.groupName = groupName;
        this.minYearOfBirth = minYearOfBirth;
        this.maxYearOfBirth = maxYearOfBirth;
        this.localizationDtoClient = localizationDtoClient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getMinYearOfBirth() {
        return minYearOfBirth;
    }

    public void setMinYearOfBirth(int minYearOfBirth) {
        this.minYearOfBirth = minYearOfBirth;
    }

    public int getMaxYearOfBirth() {
        return maxYearOfBirth;
    }

    public void setMaxYearOfBirth(int maxYearOfBirth) {
        this.maxYearOfBirth = maxYearOfBirth;
    }

    public LocalizationDtoClient getLocalization() {
        return localizationDtoClient;
    }

    public void setLocalization(LocalizationDtoClient localization) {
        this.localizationDtoClient = localization;
    }

    @Override
    public String toString() {
        return groupName + " (" + getLocalization().getLocalizationName() + ")";
    }
}
