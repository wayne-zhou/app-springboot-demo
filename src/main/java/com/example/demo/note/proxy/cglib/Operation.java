package com.example.demo.note.proxy.cglib;

public class Operation{

	public int add(int a, int b) {
		System.out.println("计算"+a+"加"+b+"="+(a+b));
		return a+b;
	}

	public int subtract(int a, int b) {
		System.out.println("计算"+a+"减"+b+"="+(a-b));
		return a-b;
	}

}
