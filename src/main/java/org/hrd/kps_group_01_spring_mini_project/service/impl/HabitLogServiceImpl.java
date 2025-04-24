package org.hrd.kps_group_01_spring_mini_project.service.impl;

import lombok.AllArgsConstructor;
import org.hrd.kps_group_01_spring_mini_project.exception.HabitLogStatusException;
import org.hrd.kps_group_01_spring_mini_project.exception.NotFoundException;
import org.hrd.kps_group_01_spring_mini_project.helper.AuthUtils;
import org.hrd.kps_group_01_spring_mini_project.model.entity.Achievement;
import org.hrd.kps_group_01_spring_mini_project.model.entity.HabitLog;
import org.hrd.kps_group_01_spring_mini_project.model.request.HabitLogRequest;
import org.hrd.kps_group_01_spring_mini_project.model.response.HabitDto;
import org.hrd.kps_group_01_spring_mini_project.repository.AchievementRepository;
import org.hrd.kps_group_01_spring_mini_project.repository.AppUserRepository;
import org.hrd.kps_group_01_spring_mini_project.repository.HabitLogRepository;
import org.hrd.kps_group_01_spring_mini_project.repository.HabitRepository;
import org.hrd.kps_group_01_spring_mini_project.service.HabitLogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class HabitLogServiceImpl implements HabitLogService {

    private final HabitLogRepository habitLogRepository;
    private final AppUserRepository appUserRepository;
    private final HabitRepository habitRepository;
    private final AuthUtils authUtils;
    private final AchievementRepository achievementRepository;

    @Override
    public HabitLog createHabitLog(HabitLogRequest habitLogRequest) {
        UUID userId = authUtils.getCurrentUserIdentifier();
        // Fetch the Habit by ID
        HabitDto habitDto = habitRepository.getHabitById(habitLogRequest.getHabitId(), userId);
        if (habitDto == null) {
            throw new NotFoundException("Habit not found for ID: " + habitLogRequest.getHabitId());
        }
        if ("COMPLETED".equalsIgnoreCase(habitLogRequest.getStatus())) {
            int xpEarned = 10;
            Integer currentXpAppUser = appUserRepository.getUserXp(userId);
            int totalXp = currentXpAppUser + xpEarned;
            int calculateLevel = totalXp / 100;
            if (calculateLevel < 0) {
                calculateLevel = 0;
            }
            // Update the user's XP and level in the database
            appUserRepository.updateUserXp(userId, totalXp, calculateLevel);
            return habitLogRepository.createHabitLog(habitLogRequest, xpEarned);
        } else if ("MISSED".equalsIgnoreCase(habitLogRequest.getStatus())) {
            int xpEarned = 0;
            return habitLogRepository.createHabitLog(habitLogRequest, xpEarned);
        } else {
            throw new HabitLogStatusException("Status must be 'COMPLETED' or 'MISSED'!");
        }
    }

    @Override
    public List<HabitLog> getAllHabitLogById(UUID Id, Integer page, Integer size) {
        UUID userId = authUtils.getCurrentUserIdentifier();
        HabitDto habitDto = habitRepository.getHabitById(Id, userId);
        List<HabitLog> habitLog = habitLogRepository.findHabitLogsById(habitDto.getHabitId(), page, size);
        if (habitLog == null) {
            throw new NotFoundException("Habit not found for ID: " + Id);
        }
        return habitLog;
    }

}
