package cn.edu.tit.bean;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class RomUtils  {

	private String romName, romerName, romInfo;

	public String getRomName() {
		return romName;
	}

	public void setRomName(String romName) {
		this.romName = romName;
	}

	public String getRomerName() {
		return romerName;
	}

	public void setRomerName(String romerName) {
		this.romerName = romerName;
	}

	public String getRomInfo() {
		return romInfo;
	}

	public void setRomInfo(String romInfo) {
		this.romInfo = romInfo;
	}

	@Override
	public String toString() {
		return "RomUtils [romName=" + romName + ", romerName=" + romerName
				+ ", romInfo=" + romInfo + "]";
	}

	
}
