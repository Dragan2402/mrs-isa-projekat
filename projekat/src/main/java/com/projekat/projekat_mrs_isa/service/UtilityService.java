package com.projekat.projekat_mrs_isa.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

public interface UtilityService {
    boolean validateEmail(String email);

    boolean validatePasswords(String password, String confirmPassword);

    boolean validatePhoneNum(String phoneNum);

    boolean containsAll(Map<String, Object> userMap);

    String getPictureEncoded(String picturePath);

    boolean validateName(String name);

    boolean validateUserData(Map<String,Object> userMap);

    boolean validateOwnerData(Map<String,Object> ownerMap);

    boolean saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException;
}
