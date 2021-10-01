package com.fruitvendorapp.utilities;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validation {

    /**
     * Method to validate email addresses
     * This method will return true if entered string is valid email.
     *
     * @param email String parameter to validate as valid email.
     * @return boolean
     */
    public static boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    /**
     * Method to validate mobile number.
     * This method will return true if entered string is valid mobile number.     *
     *
     * @param mobile String parameter to validate as valid mobile.
     * @return boolean
     */
    public static boolean isValidMobile(String mobile) {
        return (!TextUtils.isEmpty(mobile) && mobile.length() == 10);
    }

    public static boolean isValidPasswords(String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }


    public  static List<String>  isValid(String passwordhere, String confirmhere) {

        List<String> errorList = new ArrayList<String>();

        Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");

        if (!passwordhere.equals(confirmhere)) {
            errorList.add("password and confirm password does not match");
        }
        if (passwordhere.length() <= 8) {
            errorList.add("Password lenght must have alleast 8 character !!");
        }
        if (!specailCharPatten.matcher(passwordhere).find()) {
            errorList.add("Password must have atleast one specail character !!");
        }
        if (!UpperCasePatten.matcher(passwordhere).find()) {
            errorList.add("Password must have atleast one uppercase character !!");
        }
        if (!lowerCasePatten.matcher(passwordhere).find()) {
            errorList.add("Password must have atleast one lowercase character !!");
        }
        if (!digitCasePatten.matcher(passwordhere).find()) {
            errorList.add("Password must have atleast one digit character !!");
        }

        return errorList;

    }


    public static boolean isValidPhone(String phone) {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone))
        { if(phone.length() < 9 || phone.length() > 11)
            {
                check = false;
            }
            else
            {
                check = true;
            } }
        else
        {
            check=false;
        }
        return check;
    }

    /**
     * Method to validate password
     * This method will return true if entered string is valid password.
     *
     * @param password String parameter to validate as valid email.
     * @return boolean
     */
    public static boolean isValidPassword(String password) {
        return (!TextUtils.isEmpty(password) && password.length() >= 8);
    }

    public static boolean isValidABN(String abn) {
        return (!TextUtils.isEmpty(abn) && abn.length() >= 11);
    }
    public static boolean isValidACN(String acn) {
        return (!TextUtils.isEmpty(acn) && acn.length() >= 9);
    }


    public static boolean isValidPasswordz(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
    /**
     * Parse verification code
     *
     * @param message sms message
     * @return only four numbers from massage string
     */
    public static String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{4}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }


}
