package com.grass.grass.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore.Images;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.io.File;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.Locale;
import java.util.UUID;

public class Tools {

	/**
	 * 得到唯一字符串序列
	 * 
	 * @return
	 */
	public static synchronized String getNonce() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 获取android系统的版本
	 * 
	 * @return
	 */
	public static int getAndroidVersion() {
		return Build.VERSION.SDK_INT;
	}


	/**
	 * 长视频录制时间格式化
	 * 
	 * @param i
	 * @return
	 */
	public static String format(int i) {
		String s = i + "";
		if (s.length() == 1) {
			s = "0" + s;
		}
		return s;
	}

	/**
	 * 长视频录制时间格式化时、分、秒
	 * 
	 * @return string
	 */
	public static String formatTime(int hour, int minute, int second) {
		String s = "";
		if (hour == 0) {
			s = format(minute) + ":" + format(second);
		} else {
			s = format(hour) + ":" + format(minute) + ":" + format(second);
		}
		return s;
	}

	/**
	 * 格式化时间字符串
	 * 
	 * @param timeMs
	 *            毫秒
	 * @return 返回格式00:00:00
	 */
	public static String stringForTime(int timeMs) {

		StringBuilder formatBuilder = new StringBuilder();
		Formatter formatter = new Formatter(formatBuilder, Locale.getDefault());

		try {
			int totalSeconds = timeMs / 1000;

			int seconds = totalSeconds % 60;
			int minutes = (totalSeconds / 60) % 60;
			int hours = totalSeconds / 3600;

			formatBuilder.setLength(0);

			if (hours > 0) {
				return formatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
			} else {
				return formatter.format("%02d:%02d", minutes, seconds).toString();
			}
		} finally {
			formatter.close();
		}
	}

	/**
	 * 获得视频缩略图
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap createVideoThumbnail(String path) {
		Bitmap bitmap = null;
		try {
			bitmap = ThumbnailUtils.createVideoThumbnail(path, Images.Thumbnails.FULL_SCREEN_KIND);
		} catch (IllegalArgumentException ex) {
			return null;
		} catch (RuntimeException ex) {
			return null;
		} catch (OutOfMemoryError ex) {
			System.gc();
			return null;
		}
		return bitmap;
	}

	/**
	 * 获取当前程序的版本号
	 */
	public static int getVersionCode(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			return info.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 获取当前程序的版本号
	 */
	public static String getVersionName(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			return info.versionName;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取sd卡剩余空间大小
	 * 
	 * @return
	 */
	public static long getSdcardAvailableSpace() {

		if (!isSdcardAvailable()) {

			return -1;
		}

		File pathFile = Environment.getExternalStorageDirectory();

		StatFs statFs = new StatFs(pathFile.getPath());

		long block = statFs.getAvailableBlocks();

		long size = statFs.getBlockSize();

		return size * block;
	}

	/**
	 * 检查sd是否可用
	 * 
	 * @return
	 */
	public static boolean isSdcardAvailable() {
		boolean exist = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
		return exist;
	}

	/**
	 * 计算可录制视频的总时长
	 * 
	 * @param videoBiteRate
	 * @param audioBiteRate
	 * @return
	 */
	public static String getRemainRecorderTime(int videoBiteRate, int audioBiteRate) {
		long sdcardAvailableSpace = getSdcardAvailableSpace();
		int remainTime = 0;
		if (sdcardAvailableSpace != -1) {
			remainTime = (int) (sdcardAvailableSpace / ((videoBiteRate + audioBiteRate) / 8));
		}
		return recorderTimeConvert2String(remainTime);
	}

	/***
	 * 录像剩余时间 s（秒） 转换为 00:00:00
	 * 
	 * @param time
	 * @return
	 */
	public static String recorderTimeConvert2String(int time) {
		int hour = 0;
		int minute = 0;
		int second = 0;

		second = time;

		if (second > 60) {
			minute = second / 60;
			second = second % 60;
		}
		if (minute > 60) {
			hour = minute / 60;
			minute = minute % 60;
		}
		return (format(hour) + ":" + format(minute) + ":" + format(second));
	}

	/**
	 * 获取屏幕密度
	 * 
	 * @param ctx
	 * @return
	 */
	public static float getDisplayDensity(Context ctx) {
		float desity = ctx.getResources().getDisplayMetrics().density;
		return desity;
	}



	/**
	 * 得到设备名字
	 * */
	public static String getDeviceName() {
		String model = Build.MODEL;
		if (model == null || model.length() <= 0) {
			return "";
		} else {
			return model;
		}
	}

	/**
	 * 得到品牌名字
	 * */
	public static String getBrandName() {
		String brand = Build.BRAND;
		if (brand == null || brand.length() <= 0) {
			return "";
		} else {
			return brand;
		}
	}
	/**
	 * 获得手机厂商信息
	 * */
	public static String getManufacturer() {
		String brand = Build.MANUFACTURER;
		if (brand == null || brand.length() <= 0) {
			return "";
		} else {
			return brand;
		}
	}

	/**
	 * 得到操作系统版本号
	 */
	public static String getOSVersionName() {
		return Build.VERSION.RELEASE;
	}

	

	/**
	 * 获取移动设备国际识别码
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		if (context == null) {
			return "";
		}
		try {
			String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
			if (null == deviceId || deviceId.length() <= 0) {
				return "";
			} else {
				return deviceId.replace(" ", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}


	// 设备唯一标识
	public static String getDeviceId(Context context) {
		String deviceId = "";
		deviceId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		return deviceId;
	}


	/**
	 * 获取MAC地址
	 * 
	 * @param context
	 * @return
	 */
	public static String getMacAddress(Context context) {
		if (context == null) {
			return "";
		}
		try {
			String macAddress = null;
			WifiInfo wifiInfo = ((WifiManager) context.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo();
			macAddress = wifiInfo.getMacAddress();
			if (macAddress == null || macAddress.length() <= 0) {
				return "";
			} else {
				return macAddress;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取分辨率 按xxx_xxx格式输出
	 * 
	 * @param context
	 * @return
	 */
	public static String getResolution(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return new StringBuilder().append(dm.widthPixels).append("_").append(dm.heightPixels).toString();
	}

	public static long getVideoDuration(Context context, String path) {
		long duration = 0L;
		File file = new File(path);
		if (file.exists()) {
			try {
				MediaPlayer mp = MediaPlayer.create(context, Uri.parse(path));
				duration = mp.getDuration();
				mp.release();
				mp = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return duration;
	}

	/**
	 * 获取手机imsi号
	 * */
	public static String getImsi(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String _imsi = tm.getSubscriberId();
		if (_imsi != null && !_imsi.equals("")) {
			return _imsi;
		}
		return "未知";
	}


	/**
	 * 获取SIM卡运营商
	 * 
	 * @param context
	 * @return
	 */ 
	public static String getOperators(Context context) { 
	    TelephonyManager tm = (TelephonyManager) context 
	            .getSystemService(Context.TELEPHONY_SERVICE); 
	    String operator = null; 
	    String IMSI = tm.getSubscriberId(); 
	    if (IMSI == null || IMSI.equals("")) { 
	        return operator; 
	    } 
	    if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) { 
	        operator = "中国移动"; 
	    } else if (IMSI.startsWith("46001")) { 
	        operator = "中国联通"; 
	    } else if (IMSI.startsWith("46003")) { 
	        operator = "中国电信"; 
	    } 
	    return operator; 
	}

	/**
	 * 
	 * 功能:MD5加密
	 * @param s
	 */
	
	public static String getMD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str).toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 是否包含表情
	 * @param codePoint
	 * @return 如果不包含 返回false,包含 则返回true
	 */
	private static boolean isEmojiCharacter(char codePoint) {
		return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
	}
	
	/**
	 * 过滤emoji 或者 其他非文字类型的字符
	 * 
	 * @param source
	 * @return
	 */
	public static boolean filterEmoji(String source) {
		source = source +" ";

		StringBuilder buf = null;

		int len = source.length();

		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);

			if (!isEmojiCharacter(codePoint)) {// 如果不包含 则将字符append
				if (buf == null) {
					buf = new StringBuilder(source.length());
				}
				buf.append(codePoint);
			} else {
			}
		}

		if (buf == null) {
			return false;// 如果没有找到 emoji表情，则返回源字符串
		} else {
			if (buf.length() == len) {// 这里的意义在于尽可能少的toString，因为会重新生成字符串
				buf = null;
				return false;
			} else {
				return true;
			}
		}

	}
	
}
