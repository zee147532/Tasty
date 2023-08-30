package com.tasty.app.service.impl;

import com.tasty.app.service.dto.FileDTO;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MinioService {
    private final Logger log = LoggerFactory.getLogger(MinioService.class);

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    public List<FileDTO> getListObjects() {
        List<FileDTO> objects = new ArrayList<>();
        try {
            Iterable<Result<Item>> result = minioClient.listObjects(ListObjectsArgs.builder()
                .bucket(bucketName)
                .recursive(true)
                .build());
            for (Result<Item> item : result) {
                objects.add(new FileDTO()
//                        .filename(item.get().objectName())
//                        .size(item.get().size())
//                        .url(getPreSignedUrl(item.get().objectName()))
                    );
            }
            return objects;
        } catch (Exception e) {
            log.error("Happened error when get list objects from minio: ", e);
        }

        return objects;
    }

    public String getObject(String filename) {
//        InputStream stream;
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .object(filename)
                .method(Method.GET)
                .build());
        } catch (Exception e) {
            log.error("Happened error when get list objects from minio: ", e);
            return null;
        }
    }

    public String uploadFile(FileDTO request) {
        String uuidName = UUID.randomUUID().toString();
        String fileName = uuidName + "." + FilenameUtils.getExtension(request.getFile().getOriginalFilename());
        try {
            minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .stream(request.getFile().getInputStream(), request.getFile().getSize(), -1)
                .contentType("image/jpeg")
                .build());
        } catch (Exception e) {
            log.error("Happened error when upload file: ", e);
        }
        return getObject(fileName);
    }

    private String getPreSignedUrl(String filename) {
        return "http://localhost:8080/file/".concat(filename);
    }

}
