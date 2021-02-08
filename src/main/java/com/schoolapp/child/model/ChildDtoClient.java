package com.schoolapp.child.model;

import com.schoolapp.group.model.GroupDtoClient;
import com.schoolapp.parent.model.ParentDtoClient;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChildDtoClient {
    private Long id;
    private String firstName;
    private String secondName;
    private int yearOfBirth;
    private ParentDtoClient parent;
    private GroupDtoClient group;
}
