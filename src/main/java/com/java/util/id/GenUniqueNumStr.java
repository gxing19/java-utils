package com.java.util.id;

import java.util.Random;
import java.util.UUID;

public class GenUniqueNumStr {
	
	/**
	 * 根据当前时间的毫秒值(13位)+3位随机数生成16位的唯一字符串数字; 
	 * System.currentTimeMillis(),是从UTC 1970.1.1 零点到当前时间的毫秒值;
	 * System.nanoTime();原码说明有可能会越界出现负数，测了好长时间没有出现，谨慎不建议用;
	 * 
	 * @return String
	 */
	public static String getRandomNum() {

		long millis = System.currentTimeMillis();
		Random random = new Random();
		int end3 = random.nextInt(999);
		// 如果不足三位前面补0
		String randomNum = millis + String.format("%03d", end3);
		return randomNum;
	}

	/**
	 * 生产原生的36位UUID字符串,
	 * 
	 * @return String
	 */
	public static String getNativeUUID() {
		String uuidStr = UUID.randomUUID().toString();
		return uuidStr;
	}
	
	/**
	 * 生产去除连接符(-)的32位UUID字符串; 
	 * 使用String的replace()方法
	 * 
	 * @return String
	 */
	public static String getUUIDRep() {
		UUID uuid = UUID.randomUUID();
		// 去除连接符：-
		String uuidStr = uuid.toString().replace("-", "");
		return uuidStr;
	}

	/**
	 * 生产去除连接符(-)的32位UUID字符串; 
	 * 使用String的split()方法，对切割后的数组进行拼接
	 * @return String
	 */
	public static String getUUIDSplit() {
		String uuidStr = "";
		UUID uuid = UUID.randomUUID();
		String[] uuidArr = uuid.toString().split("-");
		for(int i = 0;i<uuidArr.length;i++)
		{
			uuidStr = uuidStr + uuidArr[i];
		}
		return uuidStr;
	}

	

	/**
	 * 根据UUID的hashCode值，不可靠，容易重复
	 * 
	 * @return
	 */
	public static String getOrderIdByUUId() {
		int machineId = 1;// 最大支持1-9个集群机器部署
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		System.out.println(hashCodeV);
		System.out.println(UUID.randomUUID());
		if (hashCodeV < 0) {// 有可能是负数
			hashCodeV = -hashCodeV;
		}
		// 0 代表前面补充0
		// 4 代表长度为4
		// d 代表参数为正数型
		return machineId + String.format("%015d", hashCodeV);
	}
}
