package cn.edu.tit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import cn.edu.tit.bean.Subject;
import cn.edu.tit.config.Config;

public class MySqLiteParser {
	
	static String subject_db_path = "/data"
			+ Environment.getDataDirectory().getAbsolutePath()
			+ File.separator + Config.DEFAULT_DB_PARENT_PATH + File.separator
			+ "subject.db";
	
	static String titn_db_path = "/data"
			+ Environment.getDataDirectory().getAbsolutePath()
			+ File.separator + Config.DEFAULT_DB_PARENT_PATH + File.separator
			+ "titn.db";
	
	public static List<Subject> getAllSubject(Context mContext,InputStream input) {
		List<Subject> list ;
		File db = new File(subject_db_path);
		if (!db.exists()) {
			try {
				
				InputStream is = input ;
				FileOutputStream fos = new FileOutputStream(db);
				int len = -1;
				byte[] buffer = new byte[1024];
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
					fos.flush();
				}
				fos.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
				// System.exit(0);
			}
		}

		SQLiteDatabase sqLiteDatabase = mContext.openOrCreateDatabase(subject_db_path,
				Context.MODE_PRIVATE, null);
		list = new ArrayList<Subject>();
		Cursor c = sqLiteDatabase.rawQuery("SELECT * from " + "subject", null);
		while (c.moveToNext()) {
			String mark  = c.getString(c.getColumnIndex("code"));
			String sbjname = c.getString(c.getColumnIndex("sbjname"));
			Subject sbj = new Subject(mark,sbjname);
			// System.out.println("sbjname:"+sbjname);
			list.add(sbj);
		}
		return list;
	}
	
	public static String findObj(Context mContext,InputStream input ,
			String tableName ,String columnName ,String key,String vaule){
		File db = new File(titn_db_path);
		if (!db.exists()) {
			try {
				
				InputStream is = input;
				FileOutputStream fos = new FileOutputStream(db);
				int len = -1;
				byte[] buffer = new byte[1024];
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
					fos.flush();
				}
				fos.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
				// System.exit(0);
			}
		}
		String str =null ;
		SQLiteDatabase sqLiteDatabase = mContext.openOrCreateDatabase(titn_db_path,
				Context.MODE_PRIVATE, null);
//		Cursor cursor=db.rawQuery("select * from person where personid=?", new String[]{id.toString()});//Cursor 游标和 ResultSet 很像  
		Cursor c;
		try {
			c = sqLiteDatabase.rawQuery("SELECT * from " + tableName
					+ " where " + columnName + "=?", new String[] { key });
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return str;
		}
		while (c.moveToNext()) {
			 return c.getString(c.getColumnIndex(vaule)); 
		}
		return str ;
	}

}
