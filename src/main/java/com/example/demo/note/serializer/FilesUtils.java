package com.example.demo.note.serializer;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * JDK1.7后java.nio下提供的类
 */
public class FilesUtils {
	
	/**
	 * 复制
	 * @param sourcePath
	 * @param targePath
	 * @throws IOException
	 */
	public static void copy(String sourcePath, String targePath) throws IOException{
		 Files.copy(Paths.get(sourcePath), new FileOutputStream(targePath));
	} 
	

	/**
	 * 将数据写入指定的文件
	 * @param data
	 * @param targePath
	 * @throws IOException
	 */
	public static void write(List<? extends CharSequence> data, String targePath) throws IOException{
		Files.write(Paths.get(targePath), data, Charset.forName("UTF-8"));
	}
}
