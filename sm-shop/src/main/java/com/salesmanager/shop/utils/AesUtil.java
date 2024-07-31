package com.salesmanager.shop.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class AesUtil {
	
	private static final String privateKey256 = "AES_PRIVATEKEY_FOR_SOURCING_ROOT";
	
	public byte[] encode(byte[] bytes) throws Exception {
    	SecretKey secretKey = new SecretKeySpec(privateKey256.getBytes(), "AES");
    	Cipher cipher = Cipher.getInstance("AES");
    	cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return cipher.doFinal(bytes);
	}
	
	public byte[] decode(byte[] bytes) throws Exception {
    	SecretKey secretKey = new SecretKeySpec(privateKey256.getBytes(), "AES");
    	Cipher cipher = Cipher.getInstance("AES");
    	cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(bytes);
	}
}