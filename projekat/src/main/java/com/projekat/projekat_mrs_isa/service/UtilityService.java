package com.projekat.projekat_mrs_isa.service;

import java.util.Map;

public interface UtilityService {
    boolean validateEmail(String email);

    boolean validatePasswords(String password, String confirmPassword);

    boolean validatePhoneNum(String phoneNum);

    boolean containsAll(Map<String, Object> userMap);

    String getPictureEncoded(String picturePath);
    boolean validateName(String name);
}
