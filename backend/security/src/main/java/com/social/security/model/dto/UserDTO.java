package com.social.security.model.dto;

import jakarta.validation.constraints.NotBlank;
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
public class UserDTO {
    private String fullName;

    @NotBlank(message = "{rs_security.validation.notBlank.message.email-mandatory}")
    private String email;

    private String gender;
    private String dateOfBirth;

    @NotBlank(message = "{rs_security.validation.notBlank.message.username-mandatory}")
    private String userName;

    @NotBlank(message = "{rs_security.validation.notBlank.message.password-mandatory}")
    private String password;
}
