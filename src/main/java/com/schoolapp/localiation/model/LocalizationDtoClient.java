package com.schoolapp.localiation.model;

public class LocalizationDtoClient {
    private Long id;
    private String localizationName;
    private long groupsCount;

    public LocalizationDtoClient() {
    }
    public LocalizationDtoClient(Long id, String localizationName) {
        this.id = id;
        this.localizationName = localizationName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocalizationName() {
        return localizationName;
    }

    public void setLocalizationName(String localizationName) {
        this.localizationName = localizationName;
    }

    public long getGroupsCount() {
        return groupsCount;
    }

    public void setGroupsCount(int groupsCount) {
        this.groupsCount = groupsCount;
    }

    @Override
    public String toString() {
        return localizationName;
    }
}
