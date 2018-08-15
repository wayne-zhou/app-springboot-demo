package com.example.demo.note.classLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;


/**
 * 类加载器
 * @author Administrator
 */
public class ClassLoaderTest {
	
	public static void main(String[] args) {
		/**
		//类加载器源码在JVM的入口应用类中Launcher
		//BootstrapClassLoader第一个加载，在JVM中用C实现的,加载jdk的核心类库
		System.out.println("BootstrapClassLoader 加载路径："+ System.getProperty("sun.boot.class.path"));
		//ExtClassLoader 扩展的类加载器，%JRE_HOME%\lib\ext目录下的jar包和class文件
		System.out.println("ExtClassLoader 加载路径：" + System.getProperty("java.ext.dirs"));
		//AppClassLoader 加载当前应用的jar、class
		System.out.println("AppClassLoader 加载路径：" + System.getProperty("java.class.path"));
		
		ClassLoader cl = ClassLoaderTest.class.getClassLoader();
		System.out.println("ClassLoader is :" + cl.toString());
		System.out.println("ClassLoader's parent :" + cl.getParent().toString());
		*/
		
		DiskClassLoader diskLoader = new DiskClassLoader("F:\\Program Files\\workspase3\\diskClassFile");
		 try {
			Class c = diskLoader.loadClass("Test");
			if(c != null){
				Object o = c.newInstance();
				@SuppressWarnings("unchecked")
				Method method = c.getDeclaredMethod("say", String.class);
				method.setAccessible(true);
				method.invoke(o, "张三"); //通过反射调用测试方法
				method.setAccessible(false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 自定义磁盘文件的类加载
	 */
	static class DiskClassLoader extends ClassLoader{
		private String path; //加载路径

		public DiskClassLoader(String path) {
			super();
			this.path = path;
		}

		@Override
		protected Class<?> findClass(String name) throws ClassNotFoundException {
			File file = new File(path,name+".class");
			try {
				FileInputStream is = new FileInputStream(file);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				int n = 0;
				while((n = is.read()) != -1){
					bos.write(n);
				}
				byte[] data = bos.toByteArray();
				is.close();
		        bos.close();
		        
		        return defineClass(name, data, 0, data.length);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return super.findClass(name);
		}
		
	}

}
