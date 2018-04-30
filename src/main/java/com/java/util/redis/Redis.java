package com.java.util.redis;

import java.util.UUID;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Redis {

	public static void main(String[] args) {
		test1();
		test2();
	}

	// redis单例连接使用
	public static void test1() {
		// 创建连接
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		// 设置值
		jedis.set("name", "tom");
		// 获取值
		String name = jedis.get("name");
		System.out.println(name);
		// 关闭连接
		jedis.close();
		UUID uid = UUID.randomUUID();
		System.out.println(uid);

	}

	// redis连接池的使用
	public static void test2() {
		// 1. 获取连接池配置对象
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		// 设置最大连接数
		poolConfig.setMaxTotal(10);
		// 设置最大空闲连接数
		poolConfig.setMaxIdle(5);

		// 2. 获得连接
		JedisPool jedisPool = new JedisPool(poolConfig, "127.0.0.1", 6379);
		// 3. 获得核心对象
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// 设置值
			jedis.set("age", "22");
			// 获取值
			String name = jedis.get("name");
			String age = jedis.get("age");
			System.out.println("name:" + name + " " + "age:" + age);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
			if (jedisPool != null) {
				jedisPool.close();
			}
		}
	}
}
