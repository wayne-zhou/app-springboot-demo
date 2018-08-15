package com.example.demo.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
public class NioUtil {
	
	public static String readFileByIO(File file) {
		BufferedReader br = null;
		String s = null;  
		StringBuffer sb = new StringBuffer();  
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));  
			while ((s = br.readLine()) != null) {  
				sb.append(s).append("\n");  
			}  
			return sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if(null != br){
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
 
	public static String readFileByNIO(File file) {
		// 第一步 获取通道
		FileInputStream fis = null;
		FileChannel channel = null;
		try {
			fis = new FileInputStream(file);
			channel = fis.getChannel();
			// 文件内容的大小
			int size = (int) channel.size();

			// 第二步 指定缓冲区
			ByteBuffer buffer = ByteBuffer.allocate(size);
			// 第三步 将通道中的数据读取到缓冲区中
			channel.read(buffer);

			Buffer bf = buffer.flip();
			//limit 是第一个不应该读取或写入的元素的索引即缓冲区的使用截止位置
//			System.out.println("limt:" + bf.limit());

			byte[] bt = buffer.array();
			String str = new String(bt, 0, size);
			
			buffer.clear();
			buffer = null;
			
			return str;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				channel.close();
				fis.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 利用NIO将内容输出到文件中
	 * 
	 * @param file
	 */
	public static void writeFileByNIO(String str, File file) {
		FileOutputStream fos = null;
		FileChannel fc = null;
		ByteBuffer buffer = null;
		try {
			fos = new FileOutputStream(file);
			// 第一步 获取一个通道
			fc = fos.getChannel();
			// buffer=ByteBuffer.allocate(1024);
			// 第二步 定义缓冲区
			buffer = ByteBuffer.wrap(str.getBytes());
			// 将内容写到缓冲区
			fos.flush();
			fc.write(buffer);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fc.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}