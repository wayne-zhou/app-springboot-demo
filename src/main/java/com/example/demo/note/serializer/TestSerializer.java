package com.example.demo.note.serializer;


import com.example.demo.common.utils.JsonUtil;

public class TestSerializer {
	
	public static void main(String[] args) throws Exception {
//		User user = new User("张三", 1, 20);
//		JDKSerializerUtils.serialize(user, "F:\\user.txt");
//		System.out.println("serialize end");
		
		User user2 = (User)JDKSerializerUtils.deserialize("F:\\user.txt");
		System.out.println(JsonUtil.objectToJson(user2));
		System.out.println("deserialize end");
	}

}
