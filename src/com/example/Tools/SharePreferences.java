package com.example.Tools;

import java.util.List;

import com.example.javabean.RentHousebaen;
import com.example.javabean.UserBean;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferences {
/*
   * @param islogin 判断是否有有用户登录 
   * 
   */
  public static void  login(Context context){
	  SharedPreferences preferences=context.getSharedPreferences("text",Activity.MODE_PRIVATE);
	  SharedPreferences.Editor editor=preferences.edit();
	  editor.putBoolean("islogin", true);
	  editor.commit();
  }
  
  public static boolean islogin(Context context){
	  SharedPreferences preferences=context.getSharedPreferences("text",Activity.MODE_PRIVATE);
	  //获取判断是否有用户登录，取出来islogin,默认值为false
	  return preferences.getBoolean("islogin", false);
	 
  }
  public static void setloginF(Context context){
	  SharedPreferences preferences=context.getSharedPreferences("text",Activity.MODE_PRIVATE);
	  SharedPreferences.Editor editor=preferences.edit();
	  editor.putBoolean("islogin", false);
	  editor.commit();
  }
 public static void savaUserbean(UserBean bean,Context context){
	  SharedPreferences preferences=context.getSharedPreferences("text1",Activity.MODE_PRIVATE);
	 SharedPreferences.Editor editor=preferences.edit();
     // 不转换没有 @Expose 注解的字段   
    
     Gson gson = new Gson(); 
     String userbean=gson.toJson(bean);
     editor.putString("userbean", userbean); 
     editor.commit();
 }
 public static UserBean readuserbean(Context context){
	  SharedPreferences preferences=context.getSharedPreferences("text1",Activity.MODE_PRIVATE);
	 String userbean= preferences.getString("userbean", "");
    Gson gson = new Gson();    
	return gson.fromJson(userbean, UserBean.class);
}
 
}
