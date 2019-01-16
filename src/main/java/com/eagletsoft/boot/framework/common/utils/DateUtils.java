package com.eagletsoft.boot.framework.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	public final static String DATE_FORMAT = "yyyy-MM-dd";
	public final static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static Date start(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date goPast(Date today, int seconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.SECOND, -1 * seconds);
		return cal.getTime();
	}

	public static Date goFuture(Date today, int seconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.SECOND, seconds);
		return cal.getTime();
	}
	
	public static Date next(Date today) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}
	
	public static String format(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	public static Date parse(String date, String pattern) throws Throwable {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(date);
	}
	
	public static int dayDiff(Date big, Date small) {
		big = start(big);
		small = start(small);
		
		return (int)(big.getTime() - small.getTime()) / (60 * 60 * 24 * 1000);
	}
}
