package com.example.demo.note.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class JDKSerializerUtils {
	
	/**
	 * 序列化输出到文件
	 * @param obj
	 * @param filePath
	 * @throws Exception
	 */
	public static void serialize(Object obj, String filePath) throws Exception{
		if (obj == null || filePath == null) {
			return;
		}
		FileOutputStream fos = new FileOutputStream(new File(filePath));
		ObjectOutputStream objectOut = null;
		try {
			objectOut = new ObjectOutputStream(fos);
			objectOut.writeObject(obj);
		} catch (Exception e) {
		} finally {
			try {
				if (objectOut != null)
					objectOut.close();
			} catch (Exception e) {
			} finally {
				objectOut = null;
			}
			try {
				if (fos != null)
					fos.close();
			} catch (Exception e) {
			} finally {
				fos = null;
			}
		}
	}
	
	/**
	 * 序列化返回字节数组
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static byte[] serialize(Object obj) throws Exception {
		if (obj == null) {
			return null;
		}
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ObjectOutputStream objectOut = null;
		byte[] bytes = null;
		try {
			objectOut = new ObjectOutputStream(output);
			objectOut.writeObject(obj);
			bytes = output.toByteArray();
		} catch (Exception e) {
		} finally {
			try {
				if (objectOut != null)
					objectOut.close();
			} catch (Exception e) {
			} finally {
				objectOut = null;
			}
			try {
				if (output != null)
					output.close();
			} catch (Exception e) {
			} finally {
				output = null;
			}
		}

		return bytes;
	}
	
	/**
	 * 反序列化字节文件
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static Object deserialize(String filePath) throws Exception {
		if (filePath == null) {
			return null;
		}
		FileInputStream fis = new FileInputStream(new File(filePath));
		ObjectInputStream objectIn = null;
		Object object = null;
		try {
			objectIn = new ObjectInputStream(fis);
			object = objectIn.readObject();
		} catch (Exception e) {
		} finally {
			try {
				if (objectIn != null)
					objectIn.close();
			} catch (Exception e) {
			} finally {
				objectIn = null;
			}
			try {
				if (fis != null)
					fis.close();
			} catch (Exception e) {
			} finally {
				fis = null;
			}
		}
		return object;
	}

	/**
	 * 反序列化字节数组
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Object deserialize(byte[] bytes) throws Exception {
		if ((bytes == null) || (bytes.length <= 0)) {
			return null;
		}
		ByteArrayInputStream input = new ByteArrayInputStream(bytes);
		ObjectInputStream objectIn = null;
		Object object = null;
		try {
			objectIn = new ObjectInputStream(input);
			object = objectIn.readObject();
		} catch (Exception e) {
		} finally {
			try {
				if (objectIn != null)
					objectIn.close();
			} catch (Exception e) {
			} finally {
				objectIn = null;
			}
			try {
				if (input != null)
					input.close();
			} catch (Exception e) {
			} finally {
				input = null;
			}
		}
		return object;
	}
}
