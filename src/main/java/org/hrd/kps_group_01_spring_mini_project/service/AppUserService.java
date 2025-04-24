package org.hrd.kps_group_01_spring_mini_project.service;

import org.hrd.kps_group_01_spring_mini_project.model.request.AppUserRequest;
import org.hrd.kps_group_01_spring_mini_project.model.request.AuthRequest;
import org.hrd.kps_group_01_spring_mini_project.model.response.AppUserResponse;
import org.hrd.kps_group_01_spring_mini_project.model.response.TokenResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {

}
