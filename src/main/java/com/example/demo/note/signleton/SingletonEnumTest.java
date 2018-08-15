package com.example.demo.note.signleton;

import java.io.Serializable;

/**
 * 枚举实现单例模式
 * 可序列化
 * @author Administrator
 */
public class SingletonEnumTest implements Serializable{
	
	private static final long serialVersionUID = 5797308065862483217L;

	public static void main(String[] args) {
		for(int i=0;i<5;i++){
			System.out.println(SingletonEnumTest.getInstance().hashCode());
		}
	}
	
	public static SingletonEnumTest getInstance(){
		return MyEnumSingleton.SINGLETON.getSingleton();
	}
	
	private enum MyEnumSingleton{
		SINGLETON;
		
		private SingletonEnumTest singletonEnumTest;
		
		private MyEnumSingleton() {
			this.singletonEnumTest = new SingletonEnumTest();
		}
		
		private SingletonEnumTest getSingleton(){
			return singletonEnumTest;
		}
	}
	
}
