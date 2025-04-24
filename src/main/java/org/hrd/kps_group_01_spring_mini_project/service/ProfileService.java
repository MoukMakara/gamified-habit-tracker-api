package org.hrd.kps_group_01_spring_mini_project.service;


import org.hrd.kps_group_01_spring_mini_project.model.request.ProfileRequest;
import org.hrd.kps_group_01_spring_mini_project.model.response.AppUserResponse;

public interface ProfileService {
    AppUserResponse getUserProfile();

    AppUserResponse updateUserProfile(ProfileRequest profileRequest);

    void deleteUserProfile();
}
