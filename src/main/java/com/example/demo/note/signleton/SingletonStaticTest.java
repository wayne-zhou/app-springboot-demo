package com.example.demo.note.signleton;

import java.io.Serializable;


/**
 * 静态内部类实现单例
 * 反序列化时会出现多例
 * @author Administrator
 */
public class SingletonStaticTest implements Serializable {
	
	private static final long serialVersionUID = 5324461235526652764L;
	
	public static void main(String[] args) {
		for(int i=0;i<5;i++){
			System.out.println(SingletonEnumTest.getInstance().hashCode());
		}
	}

	public static SingletonStaticTest getInstance(){
		return SingletonHandler.singletonStaticTest;
	}
	
	private static class SingletonHandler{
		private static SingletonStaticTest singletonStaticTest = new SingletonStaticTest();
	}
}
