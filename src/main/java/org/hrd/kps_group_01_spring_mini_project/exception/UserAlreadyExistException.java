package org.hrd.kps_group_01_spring_mini_project.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message) {
        super(message );
    }
}
