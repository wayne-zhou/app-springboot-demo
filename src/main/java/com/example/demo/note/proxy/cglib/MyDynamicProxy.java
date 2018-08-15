package com.example.demo.note.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class MyDynamicProxy implements MethodInterceptor {
	
	//代理的类
	private Object targeObject;
	
	public MyDynamicProxy(Object targeObject) {
		super();
		this.targeObject = targeObject;
	}
	
	public Object createProxy(){
		Enhancer enhancer = new Enhancer();//增强对象
		enhancer.setCallback(this);
		enhancer.setSuperclass(this.targeObject.getClass());
		return enhancer.create();
	}

	@Override
	public Object intercept(Object target, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		
		System.out.println("生成的代理对象："+ proxy.getClass().getName());
		System.out.println("生成的代理对象的父类："+ proxy.getClass().getSuperclass().getName());
		
//		method.invoke(this.targeObject, args); //反射执行
		Object result = proxy.invokeSuper(target, args); //调用父类的方法执行
		
		System.out.println("after execute method");
		System.out.println("执行结果："+ result.toString());
		return result;
	}

}
