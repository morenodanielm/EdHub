package com.edhub.services.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDTO {

    private String oldPassword;

    private  String token;

    @Size(min = 8)
    @NotBlank
    private String newPassword;
}
