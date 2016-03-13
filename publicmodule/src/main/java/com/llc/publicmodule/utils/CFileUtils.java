package com.llc.publicmodule.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CFileUtils {

	/**
	 * 
	 * @param file
	 *            读取的文件路径
	 * @return 返回读取到的字符串
	 */
	public String readFile(File file) {
		if (!file.exists()) {
			return "";
		}
		try {
			FileInputStream in = new FileInputStream(file);
			StringBuffer stringBuffer = new StringBuffer();
			byte[] buffer = new byte[1024];
			while (in.read(buffer, 0, buffer.length) != -1) {
				stringBuffer.append(new String(buffer));
			}
			in.close();
			return stringBuffer.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * {@code } 覆盖式 把原有的内容覆盖掉
	 * 
	 * @param file
	 *            要写入的文件
	 * @param fileName
	 *            写入的文件名 abc.txt
	 * @param string
	 *            要写入的字符串
	 *            @param isAdd 是否是追加
	 * @return
	 */
	public boolean writeFile(File file, String fileName, String string,boolean isAdd) {
		if (!file.exists()) {
			file.mkdirs();
		}
		File file2 = new File(file+"/" + fileName);
		if(!file2.exists()){
			try {
				file2.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream out = new FileOutputStream(file + "/" + fileName,isAdd);
			out.write(string.getBytes());
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * {@code} 删除文件或者文件夹
	 * 
	 * @param file
	 */
	public void deleteFile(File file) {
		if (!file.exists()) {
			return;
		}
		if (file.isFile()) {
			file.delete();
		}
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				File file2 = files[i];
				deleteFile(file2);
			}
			file.delete();
		}

	}

}
