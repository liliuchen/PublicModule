package com.llc.publicmodule.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CReadAssetsFileUtils {
/**
 * 
 * @param context
 * @param fileName 读取文件名称
 * @param filecode 编码方式 utf-8等
 * @return 返回读取的内容
 */
	public static String readLocalJson(Context context, String fileName,
			String filecode) {
		String jsonString = "";
		String resultString = "";
		try {
			InputStream inputStream = context.getResources().getAssets()
					.open(fileName);
			byte[] buffer = new byte[inputStream.available()];
			inputStream.read(buffer);
			resultString = new String(buffer, filecode);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return resultString;
	}
/**
 * 
 * @param context
 * @param fileName 文件名称
 * @return
 */
	public static String readLocalJson(Context context, String fileName) {
		String jsonString = "";
		String resultString = "";
		try {
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(context.getResources().getAssets()
							.open(fileName)));
			while ((jsonString = bufferedReader.readLine()) != null) {
				resultString += jsonString;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return resultString;
	}
}
