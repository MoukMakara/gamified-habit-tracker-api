package org.hrd.kps_group_01_spring_mini_project.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUserRequest {
    @NotBlank(message = "Username cannot be empty!")
    private String username;
    @NotBlank(message = "Email cannot be empty!")
    @Email(message = "Invalid email format!")
    private String email;
    @NotBlank(message = "Password cannot be empty!")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "Password must be at least 8 characters, contain at least one uppercase letter, one lowercase letter, one number, and one special character!")
    private String password;
    private String profileImageUrl;
}
