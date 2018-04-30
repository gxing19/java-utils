package com.java.util.json.gson;

import com.google.gson.Gson;

/**
 * Gson Demo
 * @author Rocky
 *
 */
public class GsonDemo
{
	public static void main(String[] args)
	{
		Gson gson = new Gson();
		int[] nums = {1,2,3,4,5};
		String json = gson.toJson(nums);
		System.out.println(json);
		
		String[] str = {"abc","哈哈","Hello"};
		String string = gson.toJson(str);
		System.out.println(string);
	}
}
