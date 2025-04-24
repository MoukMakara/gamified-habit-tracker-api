package org.hrd.kps_group_01_spring_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.hrd.kps_group_01_spring_mini_project.beanConfig.UUIDTypeHandler;
import org.hrd.kps_group_01_spring_mini_project.model.entity.Habit;
import org.hrd.kps_group_01_spring_mini_project.model.request.HabitRequest;
import org.hrd.kps_group_01_spring_mini_project.model.response.HabitDto;

import java.util.List;
import java.util.UUID;

@Mapper
public interface HabitRepository {
    // findUserById

    @Select("""
                SELECT * FROM habits
                WHERE app_user_id = #{userUUID}
            LIMIT #{size} OFFSET (#{page} - 1) * #{size};
        """)
    @Results(id = "habitMapper", value = {
            @Result(property = "habitId", column = "habit_id", javaType = UUID.class, typeHandler = UUIDTypeHandler.class),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "frequency", column = "frequency"),
            @Result(property = "isActive", column = "is_active"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "appUserResponse", column = "app_user_id",typeHandler = UUIDTypeHandler.class, javaType = UUID.class,
                    one = @One(select = "org.hrd.kps_group_01_spring_mini_project.repository.ProfileRepository.getUserProfile")
            )
    })
    List<HabitDto> getAllHabits(@Param("page") Integer page, @Param("size") Integer size, @Param("userUUID") UUID userUUID);


    @Select("""
                INSERT INTO habits(title, description, frequency, app_user_id)
                VALUES (#{habit.title}, #{habit.description}, #{habit.frequency}, #{userUUID})
    returning *
    """)
    @ResultMap("habitMapper")
    HabitDto createHabit(@Param("habit") HabitRequest habitRequest, @Param("userUUID") UUID userUUID);

    // get habit by habitId
    @Select("SELECT * FROM habits WHERE habit_id = #{id} and app_user_id = #{userUUID}")
    @ResultMap("habitMapper")
    HabitDto getHabitById(@Param("id") UUID habitId, @Param("userUUID") UUID userUUID);

    // get habit by habitId
    @Select("SELECT * FROM habits WHERE habit_id = #{id}")
    @ResultMap("habitMapper")
    HabitDto getHabitByIdOnly(@Param("id") UUID habitId);

    // delete habit by id
    @Delete("DELETE FROM habits WHERE habit_id = #{id} and app_user_id = #{userUUID}")
    void deleteHabitById(@Param("id") UUID habitId, @Param("userUUID") UUID userUUID);

    // update habit by id
    @Select("""
                UPDATE habits
                SET title = #{habit.title}, description = #{habit.description}, frequency = #{habit.frequency}
                WHERE habit_id = #{id} and app_user_id = #{userUUID}
                RETURNING *
            """)
    @ResultMap("habitMapper")
    HabitDto updateHabitById(@Param("id") UUID habitId, @Param("habit") HabitRequest habitRequest, @Param("userUUID") UUID userUUID);
}