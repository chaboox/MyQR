package com.deboosere.myqr.camera;

import java.util.HashSet;
import java.util.Set;

public class BarcodeData {
    private String code;
    private String format;
    private String hour;
    private String date;
    private String valideChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    public BarcodeData(String code, String format,String hour, String date){
        this.format = format;
        if(!BarcodeValide(code,valideChar ))
            this.code = code+ " ERROR";
        else
            this.code = code;
        this.hour = hour;
        this.date = date;
    }
    public void setCode(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getHour() {
        return hour;
    }

    public Boolean BarcodeValide(String code, String valideChar){
        if(format.equals("Code-128"))
            return stringToCharacterSet(valideChar).containsAll
                    (stringToCharacterSet(code))  && code.length() == 12 ;
        return stringToCharacterSet(valideChar).containsAll
                (stringToCharacterSet(code))  ;
    }
    public static Set<Character> stringToCharacterSet(String s) {
    Set<Character> set = new HashSet<>();
    for (char c : s.toCharArray()) {
        set.add(c);
    }
    return set;
}
}
