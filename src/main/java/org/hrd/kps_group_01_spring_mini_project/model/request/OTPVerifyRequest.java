package org.hrd.kps_group_01_spring_mini_project.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OTPVerifyRequest {
    private String email;
    private String otp;
}
