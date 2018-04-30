package com.java.util.encrypt;

import java.security.MessageDigest;
/**
 * SHA加密工具类
 * @author Rocky
 *
 */
public class SHAEncryptUtil {

	public final static String SHA256 = "SHA-256";
	public final static String SHA512 = "SHA-512";

	/**
	 * SHA256加密
	 * @param str 需要加密的内容
	 * @return String
	 */
	public static String getSHA256Str(String str) {
		return encryptSHA(str, SHA256);
	}

	/**
	 * SHA512加密
	 * @param str 需要加密的内容
	 * @return String
	 */
	public static String getSHA512Str(String str) {
		return encryptSHA(str, SHA512);
	}

	/**
	 * SHA加密方法
	 * @param str 需要加密字符内容
	 * @param encType 加密类型
	 * @return
	 */
	private static String encryptSHA(String str, String encType) {
		String encryptStr = null;

		try {
			// 创建加密对象，传入加密类型
			MessageDigest messageDigest = MessageDigest.getInstance(encType);
			// 传入加密字符串
			messageDigest.update(str.getBytes("UTF-8"));
			byte[] bytes = messageDigest.digest();

			StringBuffer strBufferHex = new StringBuffer();
			for (int i = 0; i < bytes.length; i++) {
				// byte转16进制
				String hexStr = Integer.toHexString(bytes[i] & 0xFF);
				if (hexStr.length() == 1) {
					strBufferHex.append("0");
				}
				strBufferHex.append(hexStr);
			}

			encryptStr = strBufferHex.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptStr;
	}

}
