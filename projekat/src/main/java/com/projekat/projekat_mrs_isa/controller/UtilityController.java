package com.projekat.projekat_mrs_isa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class UtilityController {

    public static boolean validateName(String name){
        if(name.length()<2 || name.length()>20) {
            System.out.println("Validating name: " +name+" failed. Name not 2-20 char long");
            return false;
        }
        char[] chars = name.toCharArray();
        for (char c : chars) {
            if(!Character.isLetter(c)) {
                System.out.println("Validating name: " +name+" failed. Char "+c+" not a letter");
                return false;
            }
        }
        return true;
    }

    public static boolean validateEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if( !email.matches(regex))
            System.out.println("Validating email: "+email+" failed. Email not matching the regex");
        return email.matches(regex);
    }

    public static boolean validatePasswords(String password, String confirmPassword) {
        if(password.length()<5 || password.length()>20){
            System.out.println("Validating password failed. Password not 5-20 char long");
            return false;
        }
        if(!password.matches("^[0-9A-Za-z]+$")){
            System.out.println("Validating password failed. Password does not match the regex");
            return false;
        }
        if(!password.equals(confirmPassword)){
            System.out.println("Validating password failed. Passwords do not match");
            return false;
        }
        return true;
    }

    public static boolean validatePhoneNum(String phoneNum) {
        if(!phoneNum.matches("^\\+(?:[0-9] ?){6,14}[0-9]$")) {
            System.out.println("Validating phone number failed. Phone number " + phoneNum + "does not match the regex");
            return false;
        }
        return true;
    }

    public static boolean containsAll(Map<String, Object> userMap) {
        return userMap.containsKey("firstName") && userMap.containsKey("lastName") && userMap.containsKey("email") && userMap.containsKey("username") &&
                userMap.containsKey("password") && userMap.containsKey("confirmPassword") && userMap.containsKey("address") && userMap.containsKey("city") &&
                userMap.containsKey("country") && userMap.containsKey("phoneNum");
    }
}
