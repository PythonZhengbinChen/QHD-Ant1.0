package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间转换类
 * @version 1.0 2014-8-17
 * @author 王雷
 * 
 */
public class DateConvert {
	// 七天对应的秒数
	public static final long SEVEN = 604800;
	// 一天对应的秒数
	public static final long ONE = 86400;
	// 六天对应的秒数
	public static final long SIX = 518400;
	
	/**
	 * 获得当前时间
	 * @return time 当前时间
	 */
	public static String getTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String time = df.format(new Date());
	    return time;
	}

	/**
	 * 
	 * @param time long类型时间
	 * @return MM-dd HH:mm格式
	 */
	public static String formatDateTime(long time) {
		if (0 == time) {
			return "";
		}
		SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
		return mDateFormat.format(new Date(time));
	}

	/**
	 * 秒转换为 月 日 时:分
	 * 
	 * @param millis
	 *            时间单位秒
	 * @return 月 日 时:分
	 */
	public static String longToTime(long millis) {
		if (0 == millis) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm", Locale.CHINA);
		return sdf.format(new Date(millis * 1000));
	}

	/**
	 * 秒转换为 年 月 日
	 * 
	 * @param millis
	 *            时间单位秒
	 * @return 年月日
	 */
	public static String longToDate(long millis) {
		if (0 == millis) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd", Locale.CHINA);
		return sdf.format(new Date(millis * 1000));
	}

	/**
	 * 秒转换为月 日
	 * 
	 * @param millis
	 *            时间单位秒
	 * @return 月日
	 */
	public static String longToMD(long millis) {
		if (0 == millis) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日", Locale.CHINA);
		return sdf.format(new Date(millis * 1000));
	}

	/**
	 * 毫秒转换为 年.月.日
	 * 
	 * @param millis
	 *            时间单位毫秒
	 * @return 年.月.日
	 */
	public static String longsToDate(long millis) {
		if (0 == millis) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
		return sdf.format(new Date(millis));
	}

	/**
	 * 毫秒转换为 天 小时 分
	 * 
	 * @param 要转换的毫秒数
	 * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
	 */
	public static String formatDuring(long mss) {
		int days = (int) (mss / 86400);
		int hours = (int) ((mss - 86400 * days) / 3600);
		int minutes = (int) ((mss - 86400 * days - hours * 3600) / 60);
		int seconds = (int) (mss % 60);
		String string = "";
		if (0 != days) {
			string = (days + " 天 ");
		}
		if (0 != hours) {
			string += (hours + " 小时 ");
		}
		if (0 != minutes) {
			string += (minutes + " 分 ");
		}
		if (0 != seconds) {
			string += (seconds + " 秒 ");
		}
		return string;
	}
}
