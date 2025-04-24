package org.hrd.kps_group_01_spring_mini_project.service.impl;

import lombok.RequiredArgsConstructor;
import org.hrd.kps_group_01_spring_mini_project.helper.AuthUtils;
import org.hrd.kps_group_01_spring_mini_project.model.entity.Achievement;
import org.hrd.kps_group_01_spring_mini_project.repository.AchievementRepository;
import org.hrd.kps_group_01_spring_mini_project.repository.AppUserRepository;
import org.hrd.kps_group_01_spring_mini_project.service.AchievementService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final AuthUtils authUtils;
    private final AppUserRepository appUserRepository;

    @Override
    public List<Achievement> retrievedAllAchievement(Integer page, Integer size) {
        return achievementRepository.findAllAchievement(page, size);
    }

    @Override
    public List<Achievement> retrievedAchievementByAppUserId(Integer page, Integer size) {
        UUID userUUID = authUtils.getCurrentUserIdentifier();
        //Add Achievement
        List<Achievement> achievementList = achievementRepository.findAllAchievement(page, size);
        Integer userXp = appUserRepository.getUserXp(userUUID);

        //Delete before adding (concurrency error)
        achievementRepository.deleteAchievementByAppUserId(userUUID);
        for (Achievement achievement : achievementList) {
            if (achievement.getXpRequired() <= userXp) {
                achievementRepository.insertAppUserAchievement(userUUID, achievement.getAchievementId());
            }
        }

        return achievementRepository.findAchievementByAppUserId(page, size, userUUID);
    }
}
