package com.example.Tools;

import android.content.Context;
import android.content.SharedPreferences;

public class CacheUtils {

	/**
	 * ��û����json���
	 * @param ctx
	 * @param newsCenterCategories
	 * @return
	 */
	public static String getCacheJson(Context ctx, String key_url) {
		
		SharedPreferences sp = ctx.getSharedPreferences("jsonCache", Context.MODE_PRIVATE);
		
		return  sp.getString(key_url, null);
	}

	/**
	 * ����json���
	 * @param ctx
	 * @param newsCenterCategories
	 * @param jsonStr
	 */
	public static void saveCacheJson(Context ctx, String keyUrl,String jsonStr) {
		
		SharedPreferences sp = ctx.getSharedPreferences("jsonCache", Context.MODE_PRIVATE);
		
		sp.edit().putString(keyUrl, jsonStr).commit();
		
	}

}
