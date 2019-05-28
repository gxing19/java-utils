package com.java.util.json.sfjson;
/*package com.xing.util.json.sfjson;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

public class TestJson
{	
	//把java的集合数据转换为JS识别的数组格式
	@Test
	public void json1()
	{
		List<String> list = new ArrayList();
		list.add("Java");
		list.add("JavaEE");
		list.add("IOS");
		list.add("Android");
		list.add("C++");
		list.add("PHP");
		//调用json方法，将list集合转为json格式的字符串
		String arr = JSONArray.fromObject(list).toString();
		System.out.println(arr);
//		结果：["Java","JavaEE","IOS","Android","C++","PHP"]
	}
	
	//把java中的对象转换为js识别的json对象
	@Test
	public void json2()
	{
		Student st1 = new Student(18,"Rose");
		Student st2 = new Student();
		st2.setAge(17);
		st2.setName("Kitty");
		JSONObject json = JSONObject.fromObject(st2);
		System.out.println(json);
//		结果：{"age":17,"name":"Kitty"}
	}
//	
	//将对象集合转为js能识别的json数据格式
	@Test
	public void json3()
	{
		List<Student> list = new ArrayList<>();
		list.add(new Student(17,"张三"));
		list.add(new Student(18,"李四"));
		list.add(new Student(19,"王五"));
		list.add(new Student(20,"赵六"));
		
		String jsonArr = JSONArray.fromObject(list).toString();
		System.out.println(jsonArr);
		
		结果：[
				{"age":17,"name":"张三"},
				{"age":18,"name":"李四"},
				{"age":19,"name":"王五"},
				{"age":20,"name":"赵六"}
			]
					
	}
	
	//把java中的对象类型集合数据转换为js能识别的json数据格式,并过滤字段
	@Test
	public void json4() 
	{

		List<Student> list = new ArrayList<>();
		list.add(new Student(17,"张三"));
		list.add(new Student(18,"李四"));
		list.add(new Student(19,"王五"));
		list.add(new Student(20,"赵六"));
		//json 方法，不包含条件
		JsonConfig jcf = new JsonConfig();
		//把字符转为字符对象数组
		jcf.setExcludes(new String[]{"name"});
		//转为字js可识别的json数据格式
		String jsonArr = JSONArray.fromObject(list).toString();
		com.alibaba.fastjson.JSONArray.toJSONString(list);
		System.out.println(jsonArr);	
		结果：[
				{"age":17,"name":"张三"},
				{"age":18,"name":"李四"},
				{"age":19,"name":"王五"},
				{"age":20,"name":"赵六"}
			]

	}
}
*/