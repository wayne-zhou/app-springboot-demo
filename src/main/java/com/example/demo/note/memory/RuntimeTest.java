package com.example.demo.note.memory;


/**
 * 查看jvm的内存
 * @author Administrator
 */
public class RuntimeTest {
	public static void main(String[] args) {
		Runtime myRun = Runtime.getRuntime();
		System.out.println("最大内存："+ myRun.maxMemory());
		System.out.println("已用内存："+ myRun.totalMemory());
		System.out.println("空闲内存："+ myRun.freeMemory());
		System.out.println("浪费内存中----------");
		String a ="";
		long time =System.currentTimeMillis();
		for(int i=0;i<1000;i++){
			a += i;
		}
		System.out.println("执行此程序浪费了："+(System.currentTimeMillis()-time)+"毫秒");
		System.out.println("最大内存："+ myRun.maxMemory());
		System.out.println("已用内存："+ myRun.totalMemory());
		System.out.println("空闲内存："+ myRun.freeMemory());
		myRun.gc();
		System.out.println("清理垃圾后--------");
		System.out.println("最大内存："+ myRun.maxMemory());
		System.out.println("已用内存："+ myRun.totalMemory());
		System.out.println("空闲内存："+ myRun.freeMemory());
	}

}
