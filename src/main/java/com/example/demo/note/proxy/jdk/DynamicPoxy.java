package com.example.demo.note.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collections;

public class DynamicPoxy implements InvocationHandler {
	private OperationService operation;
	
	public DynamicPoxy(OperationService operation){
		this.operation = operation;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)throws Throwable {
		System.out.println("生成的代理对象："+ proxy.getClass().getName());
		System.out.println("生成的代理对象的父类："+ proxy.getClass().getSuperclass().getName());
		
		Method[] meths = proxy.getClass().getDeclaredMethods();
		System.out.println("生成的代理对象的方法有：");
		for(Method meth : meths){
			System.out.println(meth.getName());
		}
		
		
		System.out.println("before execute method");
		System.out.println("调用方法：" + method);
		System.out.println("传入参数："+ args.toString());
		
		Object result = method.invoke(operation, args);

		System.out.println("after execute method");
		System.out.println("执行结果："+ result.toString());
		return result;
	}

}
