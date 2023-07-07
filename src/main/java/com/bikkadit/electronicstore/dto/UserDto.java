package com.bikkadit.electronicstore.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {


    private String id;

    @Size(min = 3,message = "Username must have min 3 characters")
    private String name;

    @Email(message = "Email should be in valid format")
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$",
             message = "Password must match required pattern")
    @NotEmpty
    private String password;

    @NotBlank
    private String gender;

    @NotEmpty
    private String about;

    @NotEmpty
    private String imageName;
}
