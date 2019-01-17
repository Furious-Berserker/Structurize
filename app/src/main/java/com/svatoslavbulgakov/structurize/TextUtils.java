package com.svatoslavbulgakov.structurize;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextUtils {
    public static boolean emailChecker(String text){
        return text.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    }
    public static boolean passwordChecker(String text, int length){
        if (text.length() >= length) {

            boolean isNum;
            boolean isBigLetter;
            boolean isSmallLetter;

            Pattern num = Pattern.compile("[0-9]");
            Matcher matcherNum = num.matcher(text);
            isNum = matcherNum.find();

            Pattern bigLetter = Pattern.compile("[A-Z]");
            Matcher matcherLetter = bigLetter.matcher(text);
            isBigLetter = matcherLetter.find();

            Pattern smallLetter = Pattern.compile("[a-z]");
            Matcher matcherLetterSmall = smallLetter.matcher(text);
            isSmallLetter = matcherLetterSmall.find();

            return isNum && isBigLetter && isSmallLetter;
        }
        return false;
    }
}
