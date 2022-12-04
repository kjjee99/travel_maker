package com.travelmaker.config;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Component
@RequiredArgsConstructor
public class AmazonS3ResourceStorage {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    public String store(String fullpath, MultipartFile files, String id) {
        File file = new File(MultipartUtil.getLocalHomeDirectory(), fullpath);
        try {
            files.transferTo(file);
            amazonS3Client.putObject(new PutObjectRequest(bucket, fullpath, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            return amazonS3Client.getUrl(bucket, fullpath).toString();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            if (file.exists()) file.delete();
        }
    }

}
