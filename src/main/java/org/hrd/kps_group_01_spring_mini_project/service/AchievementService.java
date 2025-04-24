package org.hrd.kps_group_01_spring_mini_project.service;

import org.hrd.kps_group_01_spring_mini_project.model.entity.Achievement;

import java.util.List;

public interface AchievementService {
    List<Achievement> retrievedAllAchievement(Integer page, Integer size);

    List<Achievement> retrievedAchievementByAppUserId(Integer page, Integer size);
}
