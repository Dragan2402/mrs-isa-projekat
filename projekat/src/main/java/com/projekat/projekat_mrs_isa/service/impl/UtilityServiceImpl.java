package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.service.UtilityService;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

@Service
public class UtilityServiceImpl implements UtilityService {
    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public boolean validateName(String name) {
        if (name.length() < 2 || name.length() > 20) {
            return false;
        }
        char[] chars = name.toCharArray();
        for (char c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean validateEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    @Override
    public boolean validatePasswords(String password, String confirmPassword) {
        if (password.length() < 5 || password.length() > 20) {

            return false;
        }
        if (!password.matches("^[0-9A-Za-z]+$")) {

            return false;
        }
        if (!password.equals(confirmPassword)) {

            return false;
        }
        return true;
    }

    @Override
    public boolean validatePhoneNum(String phoneNum) {
        if (!phoneNum.matches("^\\+(?:[0-9] ?){6,14}[0-9]$")) {

            return false;
        }
        return true;
    }

    @Override
    public boolean containsAll(Map<String, Object> userMap) {
        return userMap.containsKey("firstName") && userMap.containsKey("lastName") && userMap.containsKey("email") && userMap.containsKey("username") &&
                userMap.containsKey("password") && userMap.containsKey("confirmPassword") && userMap.containsKey("address") && userMap.containsKey("city") &&
                userMap.containsKey("country") && userMap.containsKey("phoneNum");
    }


    @Override
    public String getPictureEncoded(String picturePath) {
        Resource r = resourceLoader
                .getResource("classpath:" + picturePath);

        try {
            File file = r.getFile();
            byte[] picture = FileUtils.readFileToByteArray(file);
            return Base64.encodeBase64String(picture);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean validateUserData(Map<String,Object> userMap) {
        if (!containsAll(userMap))
            return false;

        String firstName = (String) userMap.get("firstName");
        if (!validateName(firstName))
            return false;

        String lastName = (String) userMap.get("lastName");
        if (!validateName(lastName))
            return false;

        String email = (String) userMap.get("email");
        if (!validateEmail(email))
            return false;

        String password = (String) userMap.get("password");
        String confirmPassword = (String) userMap.get("confirmPassword");
        if (!validatePasswords(password, confirmPassword))
            return false;

        String address = (String) userMap.get("address");
        if (address.length() <= 2)
            return false;

        String city = (String) userMap.get("city");
        if (city.length() <= 2)
            return false;

        String country = (String) userMap.get("country");
        if (country.length() <= 2)
            return false;

        String phoneNum = (String) userMap.get("phoneNum");
        return validatePhoneNum(phoneNum);
    }

    @Override
    public boolean validateOwnerData(Map<String,Object> ownerMap) {
        if (!validateUserData(ownerMap))
            return false;

        if (!ownerMap.containsKey("registrationReason"))
            return false;

        String registrationReason = (String) ownerMap.get("registrationReason");
        return !registrationReason.equals("");
    }

    @Override
    public boolean saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            try {
                Files.delete(filePath);
            } catch (Exception ignored) {

            }
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);

        }
    }

}
