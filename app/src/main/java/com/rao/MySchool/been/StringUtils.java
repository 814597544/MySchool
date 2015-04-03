package com.rao.MySchool.been;

import java.util.Calendar;

public class StringUtils {

	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	public static int toInt(Object obj) {
		if (obj == null)
			return 0;
		return toInt(obj.toString(), 0);
	}

	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
		}
		return 0;
	}

	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
		}
		return false;
	}
    public  Calendar formatTime(final String timeString){
        final int [] ret = new int[3];
        int index = 0;
        for(final String field : timeString.split("-")){
            ret[index] = Integer.parseInt(field);
            index++;
        }
        Calendar mformatTime = Calendar.getInstance();
        mformatTime.set(ret[0], ret[1], ret[2]);
        return mformatTime;
    }
}
