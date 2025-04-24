package org.hrd.kps_group_01_spring_mini_project.controller;

import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.hrd.kps_group_01_spring_mini_project.exception.NotFoundException;
import org.hrd.kps_group_01_spring_mini_project.model.response.ApiResponse;
import org.hrd.kps_group_01_spring_mini_project.model.response.FileDto;
import org.hrd.kps_group_01_spring_mini_project.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String fileName = fileService.uploadFile(file);

        String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/v1/files/preview-file/" + fileName)
                .toUriString();

        FileDto fileDto = FileDto.builder()
                .fileName(fileName)
                .fileUrl(fileUrl)
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .build();

        ApiResponse response = ApiResponse.builder()
                .success(true)
                .message("File uploaded successfully! Metadata of the uploaded file is returned.")
                .status(HttpStatus.CREATED)
                .payload(fileDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/preview-file/{fileName}")
    public ResponseEntity<?> viewFileByFileName(@PathVariable String fileName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        Resource resource = fileService.viewFileByFileName(fileName);

        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;


        if(fileName.endsWith(".png")) {
            mediaType = MediaType.IMAGE_PNG;
        }
        else if (fileName.endsWith(".svg")) {
            mediaType = MediaType.valueOf("image/svg+xml");
        }
        else if(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            mediaType = MediaType.IMAGE_JPEG;
        }
        else if(fileName.endsWith(".gif")) {
            mediaType = MediaType.IMAGE_GIF;
        }

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(mediaType)
                .body(resource);
    }
}