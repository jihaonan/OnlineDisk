package com.liaoyb.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class MD5 {
	public static String EncoderByMd5(String str){
		//确定计算方法
		MessageDigest md5;
		String newStr = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		
		BASE64Encoder base64en=new BASE64Encoder();
		//加密后的字符串
		 newStr=base64en.encode(md5.digest(str.getBytes("utf-8")));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newStr;
	}
	//oldpassword是在数据中存放的秘密
	public static  boolean checkpassword(String newpassword,String oldpassword){
		if(EncoderByMd5(newpassword).equals(oldpassword))
			return true;
		
		return false;
		
	}
	
	
}
