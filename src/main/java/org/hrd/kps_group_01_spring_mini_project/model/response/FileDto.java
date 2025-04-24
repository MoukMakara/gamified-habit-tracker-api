package org.hrd.kps_group_01_spring_mini_project.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileDto {
    private String fileName;
    private String fileUrl;
    private String fileType;
    private Long fileSize;
}