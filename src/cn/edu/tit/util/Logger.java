package cn.edu.tit.util;

import android.util.Log;

public class Logger {
	private static int LOGLEVEL = 6;
	private static int VERBOSE =0;
	private static int DEBUG = 1 ;
	private static int INFO = 2 ;
	private static int WARN = 3;
	private static int ERROR = 4 ;
	private static int ASSERT =5 ;
	
	public static void w(String tag, String msg) {
		if (WARN < LOGLEVEL) {
			Log.w(tag, msg);
		}
	}
	public static void d(String tag, String msg) {
		if (DEBUG < LOGLEVEL) {
			Log.d(tag, msg);
		}
	}
	public static void i(String tag, String msg) {
		if (INFO < LOGLEVEL) {
			Log.e(tag, msg);
		}
	}
	public static void e(String tag, String msg) {
		if (ERROR < LOGLEVEL) {
			Log.i(tag, msg);
		}
	}
	public static void v(String tag, String msg) {
		if (VERBOSE < LOGLEVEL) {
			Log.v(tag, msg);
		}
	}

}
