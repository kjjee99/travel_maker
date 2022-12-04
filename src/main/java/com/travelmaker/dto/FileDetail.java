package com.travelmaker.dto;

import com.travelmaker.config.MultipartUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@Builder
public class FileDetail {
    private String id;
    private String name;
    private String format;
    private String path;
    private long bytes;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public static FileDetail multipartOf(MultipartFile files){
        final String fileId = MultipartUtil.createFileId();
        final String format = MultipartUtil.getFormat(files.getContentType());

        return FileDetail.builder()
                .id(fileId)
                .name(files.getOriginalFilename())
                .format(format)
                .path(MultipartUtil.createPath(fileId, format))
                .bytes(files.getSize())
                .build();
    }
}
