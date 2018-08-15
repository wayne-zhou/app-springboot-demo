package com.example.demo.common.utils.security;


import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;


/**
 * 对称加密DES
 * @author wei.zhou.rd
 * @created 2016年12月22日
 * @version 1.0
 */
public final class DesSecurityUtil{
  private static final String CHAR_ENCODING = "UTF-8";
  private static Key key;

  
  /**
   * @param keyStr strKey:加密私钥，长度不能够小于8位
   */
  public DesSecurityUtil(String keyStr) {
	  try {
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			key  = keyFactory.generateSecret(new DESKeySpec(keyStr.getBytes(CHAR_ENCODING)));
		} catch (Exception e) {
			throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
		}
  }

  public String encrypt(String plainText){
    try{
      byte[] plainBytes = plainText.getBytes(CHAR_ENCODING);
      byte[] cipherText = getEncCode(plainBytes);
      return Base64.encodeBase64String(cipherText);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public String decrypt(String cipherText){
    try{
      byte[] decodeBase64 = Base64.decodeBase64(cipherText);
      byte[] plainBytes = getDesCode(decodeBase64);
      return new String(plainBytes, CHAR_ENCODING);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private byte[] getEncCode(byte[] plainBytes){
    try{
      Cipher cipher = Cipher.getInstance("DES");
      cipher.init(Cipher.ENCRYPT_MODE, key);
      return cipher.doFinal(plainBytes);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private byte[] getDesCode(byte[] cipherBytes){
    try{
      Cipher cipher = Cipher.getInstance("DES");
      cipher.init(Cipher.DECRYPT_MODE, key);
      return cipher.doFinal(cipherBytes);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}