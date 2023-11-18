package com.social.security.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserInfoDTO {
    private String fullName;
    private String email;
    private String gender;
    private String dateOfBirth;
    private String userName;
}
