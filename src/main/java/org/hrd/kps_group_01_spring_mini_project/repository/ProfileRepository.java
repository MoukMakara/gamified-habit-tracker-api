package org.hrd.kps_group_01_spring_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.hrd.kps_group_01_spring_mini_project.beanConfig.UUIDTypeHandler;
import org.hrd.kps_group_01_spring_mini_project.model.request.ProfileRequest;
import org.hrd.kps_group_01_spring_mini_project.model.response.AppUserResponse;

import java.util.UUID;

@Mapper
public interface ProfileRepository {

    @Select("""
            select * from app_users where app_user_id= #{userUUID};
            """)
    @Results(id = "User-Profile", value = {
            @Result(property = "appUserId", column = "app_user_id", javaType = UUID.class, typeHandler = UUIDTypeHandler.class),
            @Result(property = "profileImageUrl", column = "profile_image"),
            @Result(property = "isVerified", column = "is_verified"),
            @Result(property = "createdAt", column = "created_at")
    })
    AppUserResponse getUserProfile(@Param("userUUID") UUID userUUID);

    @Select("""
            update app_users set
            username = #{newUsername}
            where app_user_id = #{userUUID}
            returning *;
            """)
    @ResultMap("User-Profile")
    AppUserResponse updateUserProfileUsername(@Param("userUUID") UUID userUUID, String newUsername);

    @Select("""
            update app_users set
            profile_image = #{imageUrl}
            where app_user_id = #{userUUID}
            returning *;
            """)
    @ResultMap("User-Profile")
    AppUserResponse updateUserProfileImage(@Param("userUUID") UUID userUUID,String imageUrl);

    @Delete("""
            delete
            from app_users
            where app_user_id = #{userUUID};
            """)
    @ResultMap("User-Profile")
    void deleteUserProfile(@Param("userUUID") UUID userUUID);
}
