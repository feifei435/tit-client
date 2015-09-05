package cn.edu.tit.util;

import cn.edu.tit.bean.AppInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class PackageUtils {
	
	public static final String TAG = "PackageUtils";
	
	
////  获取当前程序路径
//    getApplicationContext().getFilesDir().getAbsolutePath();
//
////  获取该程序的安装包路径
//    String path=getApplicationContext().getPackageResourcePath();
//
////  获取程序默认数据库路径
//    getApplicationContext().getDatabasePath(s).getAbsolutePath();
//    

	/** 
	* 获取apk包的信息：版本号，名称，图标等 
	* @param absPath apk包的绝对路径 
	* @param context  
	*/  
	public static AppInfo apkInfo(String absPath,Context context) {  
		AppInfo info = new AppInfo();

	    PackageManager pm = context.getPackageManager();  
	    PackageInfo pkgInfo = pm.getPackageArchiveInfo(absPath,PackageManager.GET_ACTIVITIES);  
	    if (pkgInfo != null) {  
	        ApplicationInfo appInfo = pkgInfo.applicationInfo;  
	        /* 必须加这两句，不然下面icon获取是default icon而不是应用包的icon */  
	        appInfo.sourceDir = absPath;  
	        appInfo.publicSourceDir = absPath;  
	        String appName = pm.getApplicationLabel(appInfo).toString();// 得到应用名  
	        String packageName = appInfo.packageName; // 得到包名  
	        String versionName = pkgInfo.versionName; // 得到版本信息  
	        int versionCode = pkgInfo.versionCode ;
	        /* icon1和icon2其实是一样的 */  
	        Drawable icon1 = pm.getApplicationIcon(appInfo);// 得到图标信息  
	        Drawable icon2 = appInfo.loadIcon(pm);  
	        String pkgInfoStr = String.format("PackageName:%s, Vesion: %s, AppName: %s", packageName, versionName, appName);  
	        Log.i(TAG, String.format("PkgInfo: %s", pkgInfoStr));
	        info.appIcon = icon1 ;
	        info.appName = appName ;
	        info.packageName=packageName;
	        info.versionCode =versionCode  ;
	        info.versionName = versionName ;
	    }  
	    return info ;
	}
	

}
