package org.hrd.kps_group_01_spring_mini_project.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hrd.kps_group_01_spring_mini_project.exception.NotFoundException;
import org.hrd.kps_group_01_spring_mini_project.helper.AuthUtils;
import org.hrd.kps_group_01_spring_mini_project.model.entity.AppUser;
import org.hrd.kps_group_01_spring_mini_project.model.request.ProfileRequest;
import org.hrd.kps_group_01_spring_mini_project.model.response.AppUserResponse;
import org.hrd.kps_group_01_spring_mini_project.repository.AppUserRepository;
import org.hrd.kps_group_01_spring_mini_project.repository.ProfileRepository;
import org.hrd.kps_group_01_spring_mini_project.service.ProfileService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final AuthUtils authUtils;
    private final AppUserRepository appUserRepository;

    @Override
    public AppUserResponse getUserProfile() {
        UUID appUserId = authUtils.getCurrentUserIdentifier();
        AppUserResponse appUser = profileRepository.getUserProfile(appUserId);
        if (appUser == null) {
            throw new NotFoundException("User not found!");
        } else
            return appUser;

    }

    @Override
    public AppUserResponse updateUserProfile(ProfileRequest profileRequest) {
        UUID appUserId = authUtils.getCurrentUserIdentifier();
        String username = profileRequest.getUsername();
        String imageUrl = profileRequest.getProfileImageUrl();
        AppUserResponse appUser = null;
     if (!username.isEmpty()) {
          appUser = profileRepository.updateUserProfileUsername(appUserId, username);
     }if ( !imageUrl.isEmpty()) {
             appUser = profileRepository.updateUserProfileImage(appUserId, imageUrl);
        }
            if (appUser == null) {
                throw new NotFoundException("User not found!");
            } 
                return appUser;

    }

    @Override
    public void deleteUserProfile() {
        UUID appUserId = authUtils.getCurrentUserIdentifier();

        AppUser appUserResponse = appUserRepository.findUserByUUID(appUserId);

        if (appUserResponse == null) {
            throw new NotFoundException("User not found!");
        } else
            profileRepository.deleteUserProfile(appUserId);


    }
}
