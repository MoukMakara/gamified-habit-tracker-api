package org.hrd.kps_group_01_spring_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.hrd.kps_group_01_spring_mini_project.model.entity.Achievement;

import java.util.List;
import java.util.UUID;

@Mapper
public interface AchievementRepository {

    @Select("""
        select * from achievements OFFSET #{s} * (#{p} - 1) limit #{s};
    """)
    @Results(id = "achievementMapper", value = {
            @Result(property = "achievementId", column = "achievement_id"),
            @Result(property = "xpRequired" , column = "xp_required")
    })
    List<Achievement> findAllAchievement(@Param("p") Integer page,@Param("s") Integer size);

    @Select("""
        select * from achievements ac
                 inner join app_user_achievements ap on ac.achievement_id = ap.achievement_id
        where  ap.app_user_id = #{userUUID}
        order by xp_required OFFSET #{s} * (#{p} - 1) limit #{s} ;
    """)
    @ResultMap("achievementMapper")
    List<Achievement> findAchievementByAppUserId(@Param("p") Integer page,@Param("s") Integer size, UUID userUUID);

    @Delete("delete from app_user_achievements where app_user_id = #{userUUID}")
    void deleteAchievementByAppUserId(@Param("userUUID") UUID userUUID);
    //Insert User Achievement
    @Insert("INSERT INTO app_user_achievements(app_user_id, achievement_id) values (#{userId}, #{achievementId})")
    void insertAppUserAchievement(@Param("userId") UUID userId, @Param("achievementId") UUID achievementId);

}
