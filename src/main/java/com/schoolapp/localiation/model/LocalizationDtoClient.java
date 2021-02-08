package com.schoolapp.localiation.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LocalizationDtoClient {
    private Long id;
    private String localizationName;
    private long groupsCount;

}
