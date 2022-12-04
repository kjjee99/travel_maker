package com.travelmaker.config;

import org.springframework.util.StringUtils;

import java.util.UUID;

public class MultipartUtil {

    private static final String BASE_DIR = "Downloads";

    /**
     * 로컬에서 사용자의 홈 디렉터리를 반환
     * */
    public static String getLocalHomeDirectory(){
        return System.getProperty("user.home");
    }


    /**
     * 새로운 파일 고유 ID 생성
     * @return 36자리 UUID
     * */
    public static String createFileId(){
        return UUID.randomUUID().toString();
    }

    /**
     * Multipart 의 Content Type 값에서 / 이후 확장자만 잘라냄
     * @Param content type ex) image/png
     * @return ex) png
     * */
    public static String getFormat(String contentType){
        if(StringUtils.hasText(contentType)){
            return contentType.substring(contentType.lastIndexOf('/') + 1);
        }
        return null;
    }

    /**
     * 파일의 전체 경로 생성
     * @Param fileId 생성된 file 고유 ID
     * @Param format 확장자
     * */
    public static String createPath(String fileId, String format){
        return String.format("%s/%s.%s", BASE_DIR, fileId, format);
    }

}
