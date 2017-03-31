package com.example.Tools;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TextUtil {
	// 验证邮箱格式是否正确
	private static long lastClickTime;
	public static boolean emailFormat(String email) {
		Pattern pattern = Pattern
				.compile("^[A-Za-z\\d]+(\\.[A-Za-z\\d]+)*@([\\dA-Za-z](-[\\dA-Za-z])?)+(\\.{1,2}[A-Za-z]+)+$");
		Matcher mc = pattern.matcher(email);
		return mc.matches();
	}
	public static void setTextOfTextView(View v, String text) {
		TextView textView = (TextView) v;
		if (textView != null) {
			textView.setText(text == null ? "" : text);
		}
	}
	// 验证手机格式是否正确

	public static boolean isTelphone(String tel) {
		Pattern pattern = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$");
		Matcher mc = pattern.matcher(tel);
		return mc.matches();
	}

	// 验证是否却不都是数字

	public static boolean isNum(String num) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher mc = pattern.matcher(num);
		return mc.matches();
	}

	/**
	 * ����ĸ��ͷ��������3~16֮�䣬ֻ�ܰ����ַ������ֺ��»��ߣ�w��
	 * 
	 * @param password
	 * @return
	 */
	private static boolean passwordFormat(String password) {
		Pattern pattern = Pattern
				.compile("^[\\@A-Za-z0-9\\!\\#\\$\\%\\^\\&\\*\\.\\~]{6,15}$");
		Matcher mc = pattern.matcher(password);
		return mc.matches();
	}

	public static boolean isnull(Activity activity, String text, String textto) {
		if (text.equals(textto)) {
			return true;
		} else {
			return false;
		}

	}

	public static boolean nameFormat(String name) {
		Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5A-Za-z0-9_]{3,16}$");
		Matcher mc = pattern.matcher(name);
		return mc.matches();
	}

	/**
	 * ��ȡ�ַ����ȣ������ա������ַ�����Ϊ2��ASCII����ַ�����Ϊ1
	 * 
	 * @param c
	 *            �ַ�
	 * @return �ַ�����
	 */

	private static int getSpecialCharLength(char c) {
		if (isLetter(c)) {
			return 1;
		} else {
			return 2;
		}
	}

	/**
	 * �ж�һ���ַ���Ascill�ַ����������ַ����纺���գ������ַ���
	 * 
	 * @param char c, ��Ҫ�жϵ��ַ�
	 * @return boolean, ����true,Ascill�ַ�
	 */
	private static boolean isLetter(char c) {
		int k = 0x80;
		return c / k == 0 ? true : false;
	}

	public static boolean checkName(Activity context, String name) {
		if (TextUtils.isEmpty(name) || name.length() < 3 || name.length() > 16
				|| !nameFormat(name)) {
			Toast.makeText(context, "用户名为3-16的数字或者字符", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		return true;
	}

	public static boolean checkEmail(Activity context, String email) {
		if (!emailFormat(email) || email.length() > 31) {
			Toast.makeText(context, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	public static boolean checkPassword(Activity context, String password) {
		if (!passwordFormat(password)) {
			Toast.makeText(context, "密码为6-15的数字或者字母", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		return true;
	}
public static String getData(){
	 Calendar c = Calendar.getInstance();
	 int year=c.get(Calendar.YEAR);
	 int month=c.get(Calendar.MONTH)+1;
	 int day=c.get(Calendar.DAY_OF_MONTH);
	int hour = c.get(Calendar.HOUR);
    int minute = c.get(Calendar.MINUTE);
	String date=year+"/"+month+"/"+day+"  "+hour+":"+minute;
	return date;
}

    //synchronized每次只能有一个线程来调用该方法,防止双点击事件的发生

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();   
        if ( time - lastClickTime < 500) {   
            return true;   
        }   
        lastClickTime = time;   
        return false;   
    }

}
