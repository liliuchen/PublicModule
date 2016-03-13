package com.llc.publicmodule.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

public class CPhoneInfo {
	Context context;
	static String ip;
	static String mac;
	static String phonenum;
	static WifiManager manager;
	static TelephonyManager telephonyManager;
	static PackageManager packageManager;
	static WifiInfo wifiInfo;
	static String imei;
	static String imsi;
	static String version;
	
	

	public static void setPhonenum(String phonenum) {
		CPhoneInfo.phonenum = phonenum;
	}

	public CPhoneInfo(Context context) {
		super();
		this.context = context;
		manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		packageManager=context.getPackageManager();
		initData();

	}

	public static void initData() {
		wifiInfo = manager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		mac = wifiInfo.getMacAddress();
		ip = intToIp(ipAddress);
		phonenum = telephonyManager.getLine1Number();
		imei=telephonyManager.getDeviceId();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo("com.pronetway.wifi", 0);
			version= packageInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static String intToIp(int i) {

		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + (i >> 24 & 0xFF);
	}

	public static String getIp() {
		return ip;
	}

	public static String getMac() {
		return mac;
	}

	public static String getPhonenum() {
		return phonenum;
	}
	public static String getImei() {
		return imei;
	}

	public static String getImsi() {
		return imsi;
	}

	public static String getVersion() {
		return version;
	}
	
	
}
