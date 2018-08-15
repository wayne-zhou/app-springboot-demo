package com.example.demo.note.proxy;

import com.example.demo.note.proxy.cglib.MyDynamicProxy;
import com.example.demo.note.proxy.cglib.Operation;
import com.example.demo.note.proxy.jdk.DynamicPoxy;
import com.example.demo.note.proxy.jdk.OperationService;
import com.example.demo.note.proxy.jdk.OperationServiceImpl;

import java.lang.reflect.Proxy;


public class Client {
	public static void main(String[] args) {
		//jdk poxy
		OperationService operation = new OperationServiceImpl();
		DynamicPoxy jdkPoxy = new DynamicPoxy(operation);
		//通过Proxy类代理加载对象
		OperationService oper = (OperationService)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), 
				operation.getClass().getInterfaces(), jdkPoxy);
		System.out.println(oper.add(1, 2));
		
		//cglib poxy
		Operation o = new Operation ();
		MyDynamicProxy cglibPoxy = new MyDynamicProxy(o);
		Operation op = (Operation)cglibPoxy.createProxy();
		System.out.println(op.subtract(5, 1));

	}

}
