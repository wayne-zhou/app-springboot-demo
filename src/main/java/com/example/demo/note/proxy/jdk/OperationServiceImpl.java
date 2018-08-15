package com.example.demo.note.proxy.jdk;

public class OperationServiceImpl implements OperationService {

	@Override
	public int add(int a, int b) {
		System.out.println("计算"+a+"加"+b+"="+(a+b));
		return a+b;
	}

	@Override
	public int subtract(int a, int b) {
		System.out.println("计算"+a+"减"+b+"="+(a-b));
		return a-b;
	}

}
