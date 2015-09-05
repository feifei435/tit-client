package cn.edu.tit.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.edu.tit.config.Config;

public class StreamUtils {

	public static  byte[] inputStreamToByte(InputStream is) throws IOException {
		ByteArrayOutputStream bAOutputStream = new ByteArrayOutputStream();
		int ch;
		while ((ch = is.read()) != -1) {
			bAOutputStream.write(ch);
		}
		byte data[] = bAOutputStream.toByteArray();
		bAOutputStream.close();
		return data;
	}

	public static String inputStreamToString(InputStream is) throws IOException {
		ByteArrayOutputStream bAOutputStream = new ByteArrayOutputStream();
		int ch;
		while ((ch = is.read()) != -1) {
			bAOutputStream.write(ch);
		}
		String data = bAOutputStream.toString(Config.CHARSET_GBK);
		bAOutputStream.close();
		return data;
	}

	public static InputStream getInputStream(String str) throws Exception {
		return new ByteArrayInputStream(str.getBytes(Config.CHARSET_GBK));
	}

}
