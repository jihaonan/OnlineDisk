package com.liaoyb.util;

import net.sf.json.JSONObject;

public class Json {
	public static String writeMess(String mess){
		JSONObject jo = new JSONObject();
		jo.put("mess", mess);
		return jo.toString();
		
	}
	
	public static String writeLogState(String state){
		JSONObject jo = new JSONObject();
		jo.put("state", state);
		return jo.toString();
	}
}
