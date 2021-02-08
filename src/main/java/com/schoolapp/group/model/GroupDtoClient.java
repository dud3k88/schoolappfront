package com.schoolapp.group.model;

import com.schoolapp.localiation.model.LocalizationDtoClient;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GroupDtoClient {
    private Long id;
    private String groupName;
    private int minYearOfBirth;
    private int maxYearOfBirth;
    private LocalizationDtoClient localizationDtoClient;
}
