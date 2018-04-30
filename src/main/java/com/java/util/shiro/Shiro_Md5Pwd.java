package com.java.util.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @class: Shiro_Md5Pwd 
 * @desc: md5hash加密
 * @author gxing
 * @date 2018年4月29日
 */
public class Shiro_Md5Pwd
{
	public static void main(String[] args)
	{
		Md5Hash md5Pwd = new Md5Hash("admin", "admin", 2);
//		Md5Hash md5Pwd = new Md5Hash("admin", "", 2);
		System.out.println(md5Pwd.toString());
	}
}

