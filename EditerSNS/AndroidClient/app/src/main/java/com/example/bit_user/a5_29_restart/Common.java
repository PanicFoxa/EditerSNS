package com.example.bit_user.a5_29_restart;


import android.content.SharedPreferences;
import android.preference.PreferenceActivity;

public class Common extends PreferenceActivity {

	/* 환경설정 관련 변수 */
	SharedPreferences pref;
	//activity 배경색상
	public static int bgColor=-1; // 흰색
	//본문 글자색상
	public static int textColor=-16777216; // 검정색
	//색상 다이얼로그 변수명
	public static String dialogName="";
	//색상 다이얼로그 선택 숫자값
	public static int color_num;
	public static String color_black = "ColorStateList{mThemeAttrs=nullmChangingConfigurations=0mStateSpecs=[[]]mColors=[-16777216]mDefaultColor=-16777216}";
	public static String color_none = "ColorStateList{mThemeAttrs=nullmChangingConfigurations=0mStateSpecs=[[-16842910], []]mColors=[973078528, -570425344]mDefaultColor=-570425344}";
	public static String color_white = "ColorStateList{mThemeAttrs=nullmChangingConfigurations=0mStateSpecs=[[]]mColors=[-1]mDefaultColor=-1}";

	// 서버 주소
	public static String server_url="http://192.168.1.19:80";
}