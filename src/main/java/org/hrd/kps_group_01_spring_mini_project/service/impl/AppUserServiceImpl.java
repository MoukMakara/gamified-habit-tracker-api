package org.hrd.kps_group_01_spring_mini_project.service.impl;

import lombok.RequiredArgsConstructor;

import org.hrd.kps_group_01_spring_mini_project.repository.AppUserRepository;
import org.hrd.kps_group_01_spring_mini_project.service.AppUserService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        return appUserRepository.findUserByIdentifier(identifier);
    }
}
