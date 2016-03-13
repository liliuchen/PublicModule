package com.llc.publicmodule.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CGetVeriCode {
	public static String getNumber(String str){
		String regEx = "\\d{4,6}";
	    Pattern p = Pattern.compile(regEx);
	    Matcher m = p.matcher(str.toString());
        if(m.find()){
            String vericode=m.group();
            return vericode;
        }
	    return "";
	}
}
