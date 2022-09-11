package com.company;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Str {
    private String str;

    public Str(String str) {
        this.str = str;
    }

    public Item deal() {
        char sign = '+';
        String num = "0";
        String index = "0";
        Pattern p = Pattern.compile("[+-]{0,2}[0-9]+");
        Matcher m = p.matcher(str);
        ArrayList<String> dig = new ArrayList<>();
        while (m.find()) {
            dig.add(m.group());
        }
        if (dig.size() == 2) {
            sign = dig.get(0).charAt(0);
            num = dig.get(0).substring(1);
            index = dig.get(1);
        }
        if (dig.size() == 1 && str.contains("**")) {
            if (str.startsWith("+") || str.startsWith("-"))
            { sign = str.charAt(0); }
            if (str.charAt(1) == '-') { num = "-1"; }
            else {
                num = "1"; }
            index = dig.get(0);
        }
        if (dig.size() == 1 && !str.contains("**") && str.contains("x")) {
            sign = dig.get(0).charAt(0);
            num = dig.get(0).substring(1);
            index = "1";
        }
        if (dig.size() == 1 && !str.contains("x")) {
            sign = dig.get(0).charAt(0);
            num = dig.get(0).substring(1);
            index = "0";
        }
        if (dig.size() == 0 && str.contains("x")) {
            if (str.startsWith("+") || str.startsWith("-"))
            { sign = str.charAt(0); }
            if (str.charAt(1) == '-') { num = "-1"; }
            else {
                num = "1"; }
            index = "1";
        }
        Item fac = new Item(sign,num,index);
        return fac;
    }
}
