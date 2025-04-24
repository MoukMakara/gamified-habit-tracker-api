package org.hrd.kps_group_01_spring_mini_project.service.impl;

import io.minio.*;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.hrd.kps_group_01_spring_mini_project.exception.NotFoundException;
import org.hrd.kps_group_01_spring_mini_project.service.FileService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final String bucketName = "spring";
    private final MinioClient minioClient;

    @Override
    public String uploadFile(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException,
            IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        String originalFileName = file.getOriginalFilename();

        if (originalFileName == null || originalFileName.isEmpty()) {
            throw new NotFoundException("Profile image is required.");
        }

        String extension = StringUtils.getFilenameExtension(originalFileName);
        if (extension == null || extension.isEmpty()) {
            throw new NotFoundException("Invalid file type.");
        }

        extension = extension.toLowerCase(); // normalize

        if (!(extension.equals("png") ||
                extension.equals("svg") ||
                extension.equals("jpg") ||
                extension.equals("jpeg") ||
                extension.equals("gif"))) {
            throw new NotFoundException("Profile image must be .png, .svg, .jpg, .jpeg, or .gif");
        }

        String newFileName = UUID.randomUUID() + "." + extension;

        // Check if bucket exists
        BucketExistsArgs bucket = BucketExistsArgs.builder()
                .bucket(bucketName)
                .build();
        boolean isBucketExists = minioClient.bucketExists(bucket);

        if (!isBucketExists) {
            MakeBucketArgs makeBucket = MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build();
            minioClient.makeBucket(makeBucket);
        }

        // Upload the file
        PutObjectArgs objectArgs = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(newFileName)
                .contentType(file.getContentType())
                .stream(file.getInputStream(), file.getSize(), -1)
                .build();

        ObjectWriteResponse response = minioClient.putObject(objectArgs);

        return response.object();
    }


    @Override
    public Resource viewFileByFileName(String fileName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        if (fileName == null || fileName.isEmpty()) {
            throw new NotFoundException("Profile image is required.");
        }

        String lowerCaseFileName = fileName.toLowerCase();
        if (
                !lowerCaseFileName.endsWith(".png") &&
                        !lowerCaseFileName.endsWith(".svg") &&
                        !lowerCaseFileName.endsWith(".jpg") &&
                        !lowerCaseFileName.endsWith(".jpeg") &&
                        !lowerCaseFileName.endsWith(".gif")
        ) {
            throw new NotFoundException("Profile image must end with .png, .svg, .jpg, .jpeg, or .gif");
        }

        GetObjectArgs object = GetObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .build();

        InputStream result = minioClient.getObject(object);


        return new InputStreamResource(result);
    }
}
