package com.example.administrator.wilddog_login;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Shared {
	private static final String CONFIGS = "configs";//SharedPreferences������xml�ļ���config.xml
	public static final String DEFINE_EMAIL = "defind_username";//�����ʺŵ�key
	public static final String DEFINE_PASSWORD = "defind_password";//���������key
	public static final String DEFINE_IS_REMEMBER = "defind_is_remember";//checkBox��״̬����
	private static SharedPreferences msp;

	private static SharedPreferences getSharedPreferencesCheck(Context context){
		if(msp==null){
			msp = context.getSharedPreferences(CONFIGS, Context.MODE_PRIVATE);
		}
		return msp;
	}
	

	public static String getString(Context context,String key){
		SharedPreferences sp = getSharedPreferencesCheck(context);
		return sp.getString(key, null);
	}

	public static void putString(Context context,String key,String value){
		SharedPreferences sp = getSharedPreferencesCheck(context);
		sp.edit().putString(key, value).commit();
	}

	public static boolean getBoolean(Context context,String key){
		SharedPreferences sp = getSharedPreferencesCheck(context);
		return sp.getBoolean(key, false);
	}

	public static void putBoolean(Context context,String key, Boolean value){
		SharedPreferences sp = getSharedPreferencesCheck(context);
		sp.edit().putBoolean(key, value).commit();
	}
	//store int
	public static void putInt(Context context, String key, int value){
		SharedPreferences sp=getSharedPreferencesCheck(context);
		sp.edit().putInt(key, value).commit();
	}
	
	//get int
	public static int getInt(Context context, String key){
		SharedPreferences sp = getSharedPreferencesCheck(context);
		return sp.getInt(key, -1);
	}

	public static void deleteSpDate(Context context){
		SharedPreferences sp = getSharedPreferencesCheck(context);
		sp.edit().clear().commit();
		Toast.makeText(context, "ɾ���ɹ�", 0).show();
	}
}
