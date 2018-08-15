package com.example.demo.common.utils.security;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 非对称加密RSA
 * @author wei.zhou.rd
 * @created 2016年12月22日
 * @version 1.0
 */
public class RsaSecurityUtil {
	//签名算法
	public static final String KEY_ALGORITHM = "RSA";  
	//签名算法 
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    private static final String PUBLIC_KEY = "publicKey";  
    private static final String PRIVATE_KEY = "privateKey";  
	
	
    
    /** 
     * 公钥加密
     * @param publicKeyStr 公钥（base64位编码）
     * @param plainTextData
     * @return
     * @throws Exception
     */
    public static byte[] encryptOfPubKey(String publicKeyStr, byte[] plainTextData) throws Exception {  
    	RSAPublicKey publicKey = loadPublicKeyByStr(publicKeyStr);
        if (publicKey == null) {  
            throw new Exception("加密公钥为空, 请设置");  
        }  
        return encrypt(publicKey, plainTextData);
    }     
    
    /** 
     * 私钥加密
     * @param privateKeyStr 私钥（base64位编码）
     * @param plainTextData
     * @return
     * @throws Exception
     */
    public static byte[] encryptOfPriKey(String privateKeyStr, byte[] plainTextData) throws Exception { 
    	RSAPrivateKey privateKey = loadPrivateKeyByStr(privateKeyStr);
        if (privateKey == null) {  
            throw new Exception("加密私钥为空, 请设置");  
        }  
        return encrypt(privateKey, plainTextData);
    } 
    
    /** 
     * 私钥解密
     * @param privateKeyStr 私钥（base64位编码）
     * @param cipherData
     * @return
     * @throws Exception
     */
    public static byte[] decryptOfPriKey(String privateKeyStr, byte[] cipherData) throws Exception {  
    	RSAPrivateKey privateKey = loadPrivateKeyByStr(privateKeyStr);
        if (privateKey == null) {  
            throw new Exception("解密私钥为空, 请设置");  
        }  
        return decrypt(privateKey, cipherData);
    }  
  
    /** 
     * 公钥解密
     * @param publicKeyStr 公钥（base64位编码）
     * @param cipherData
     * @return
     * @throws Exception
     */
    public static byte[] decryptOfPubKey(String publicKeyStr, byte[] cipherData) throws Exception {  
    	RSAPublicKey publicKey = loadPublicKeyByStr(publicKeyStr);
        if (publicKey == null) {  
            throw new Exception("解密公钥为空, 请设置");  
        }  
        return decrypt(publicKey, cipherData);
    }  
    
    /**
     * 密钥加密
     * @param key 公钥/私钥
     * @param plainTextData
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(Key key, byte[] plainTextData) throws Exception{
    	if (key == null) {  
            throw new Exception("加密密钥为空, 请设置");  
        } 
    	 Cipher cipher = null;  
         try {  
             // 使用默认RSA  
             cipher = Cipher.getInstance(KEY_ALGORITHM);  
             cipher.init(Cipher.ENCRYPT_MODE, key);  
             byte[] output = cipher.doFinal(plainTextData);  
             return output;
         } catch (NoSuchAlgorithmException e) {  
             throw new Exception("无此加密算法");  
         } catch (NoSuchPaddingException e) {  
             e.printStackTrace();  
             return null;  
         } catch (InvalidKeyException e) {  
             throw new Exception("加密密钥非法,请检查");  
         } catch (IllegalBlockSizeException e) {  
             throw new Exception("明文长度非法");  
         } catch (BadPaddingException e) {  
             throw new Exception("明文数据已损坏");  
         }  
    }
    
    /**
     * 密钥解密
     * @param key 公钥/私钥
     * @param cipherData
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(Key key, byte[] cipherData) throws Exception{
    	if (key == null) {  
            throw new Exception("解密密钥为空, 请设置");  
        } 
    	Cipher cipher = null;  
        try {  
            // 使用默认RSA  
            cipher = Cipher.getInstance(KEY_ALGORITHM);  
            cipher.init(Cipher.DECRYPT_MODE, key);  
            byte[] output = cipher.doFinal(cipherData);  
            return output;
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此解密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("解密密钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("密文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("密文数据已损坏");  
        } 
    }
    
    
    
    
    /**
     * RSA进行签名
     * @param privateKeyStr 私钥（base64位编码）
     * @param data 待签名数据
     * @return
     * @throws Exception
     */
    public static String sign(String privateKeyStr, String data) throws Exception{
    	RSAPrivateKey privateKey = loadPrivateKeyByStr(privateKeyStr);
    	try{  
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);  
            signature.initSign(privateKey);  
            signature.update(data.getBytes());  
            byte[] signed = signature.sign();  
            return Base64.encodeBase64String(signed);
        } catch (Exception e) { 
        	e.printStackTrace();
            throw new Exception("签名异常"); 
        }  
    }
    
    /**
     * RSA签名验证
     * @param publicKeyStr 公钥（base64位编码）
     * @param data  待签名数据
     * @param sign  签名
     * @return
     * @throws Exception
     */
    public static boolean doCheck(String publicKeyStr, String data, String sign) throws Exception{
    	RSAPublicKey publicKey = loadPublicKeyByStr(publicKeyStr);
    	Signature signature = Signature.getInstance(SIGN_ALGORITHMS);  
        signature.initVerify(publicKey);  
        signature.update(data.getBytes());  
        return signature.verify(Base64.decodeBase64(sign));
    }
    
    /** 
     * 从字符串中加载公钥 
     * @param publicKeyStr 公钥数据字符串 
     * @throws Exception  加载公钥时产生的异常 
     */  
    public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr)  
            throws Exception {  
        try {  
            byte[] buffer = Base64.decodeBase64(publicKeyStr);  
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);  
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("公钥非法");  
        } catch (NullPointerException e) {  
            throw new Exception("公钥数据为空");  
        }  
    }  
  
    /** 
     * 从字符串中加载私钥
     * @param privateKeyStr 私钥数据字符串 
     * @throws Exception  加载私钥时产生的异常 
     */ 
    public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr) throws Exception {  
        try {  
            byte[] buffer = Base64.decodeBase64(privateKeyStr);  
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);  
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("私钥非法");  
        } catch (NullPointerException e) {  
            throw new Exception("私钥数据为空");  
        }  
    } 
    
    /** 
     * 初始化密钥，获得密钥对
     * @return 
     * @throws Exception 
     */  
    public static Map<String, Object> initKey() throws Exception {  
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM); 
        //密钥长度，单位：bit 密钥大小为96-1024位,默认512
        keyPairGen.initialize(512, new SecureRandom());  
        KeyPair keyPair = keyPairGen.generateKeyPair(); 
        
        // 公钥  
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
        // 私钥  
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();  
  
        Map<String, Object> keyMap = new HashMap<String, Object>(2);  
        keyMap.put(PUBLIC_KEY, new String(Base64.encodeBase64String(publicKey.getEncoded())));  
        keyMap.put(PRIVATE_KEY, new String(Base64.encodeBase64String(privateKey.getEncoded())));  
        return keyMap;  
    }
    
}
