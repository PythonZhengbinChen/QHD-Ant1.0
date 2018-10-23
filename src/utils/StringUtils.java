package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by liyuan.zhou on 2015/10/26.
 */
public class StringUtils {

    public static String getQueryParam(String str) {
//        try {
//            return new String(str.getBytes("ISO-8859-1"), "UTF-8");
        	return str;
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            return null;
//       }
    }

    public static Calendar getCalendar(String str,String format) {
        try {
            Date date = new SimpleDateFormat(format).parse(str);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            return null;
        }
    }
}
