package nohi.utils;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * DateUtils
 *
 * @author nohi
 * 日期工具类
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static final String PATTERN = "yyyyMM";

    public static final String SIMPLE_DATE = "yyyyMMdd";

    public static final String HYPHEN_DATE = "yyyy-MM-dd";

    public static final String HYPHEN_TIME = "yyyy-MM-dd HH:mm:ss";

    public static final String SLASH_DATE = "yyyy/MM/dd";

    public static final String DOTTED_DATE = "yyyy.MM.dd";

    public static final String TIMESTAMP = "yyyyMMddHHmmssSSS";
    public static final int DATE_LEN_8 = 8;


    /**
     * 把文本字符串解析成日期对象
     *
     * @param value   字符串
     * @param pattern 日期pattern
     */
    public static Date parseDate(String value, String pattern) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String format(Date date) {
        return format(date, HYPHEN_DATE);
    }

    /**
     * 把日期对象转换成字符串
     *
     * @param date    日期对象
     * @param pattern 日期pattern
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date getLastDateOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    /**
     * 获取当前时间
     */
    public static Date getNow() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 获取当前时间,8位字符串 yyyyMMdd
     */
    public static String getNowStr() {
        return format(getNow(), SIMPLE_DATE);
    }

    /**
     * 获取当前日期是年中的第几周
     */
    public static int getWeekOfYear() {
        return getWeekOfYear(new Date());
    }

    /**
     * 获取当前日期是年中的第几周
     */
    public static int getWeekOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取当前日期是年中的第几天
     */
    public static int getDayOfYear() {
        return getDayOfYear(new Date());
    }

    /**
     * 获取当前日期是年中的第几天
     */
    public static int getDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public static Timestamp getTimestamp() {
        return new Timestamp(new Date(System.currentTimeMillis()).getTime());
    }

    public static String getCurrentYear() {
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }

    public static String getCurrentMonth() {
        return String.valueOf(Calendar.getInstance().get(Calendar.MONTH));
    }

    /**
     * 获取上个月
     *
     * @param cal
     * @author NOHI
     */
    public static String preMon(Calendar cal) {
        cal.add(Calendar.MONTH, -1);
        return DateUtils.format(cal.getTime(), PATTERN);
    }

    /**
     * 获取前几年
     *
     * @param cal
     * @param add
     * @author NOHI
     */
    public static String preYear(Calendar cal, int add) {
        cal.add(Calendar.YEAR, add);
        return DateUtils.format(cal.getTime(), "yyyy");
    }

    /**
     * 日期加N天
     *
     * @param sDate
     */
    public static String addDay(String sDate, int days) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(sDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + days);

        String nextDay = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
        return nextDay;
    }

    /**
     * Date -> LocalDate
     *
     * @param date
     */
    public static LocalDate date2LocalDate(Date date) {
        return date2LocalDateTime(date).toLocalDate();
    }

    /**
     * Date -> LocalDateTime
     *
     * @param date
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        if (null == date) {
            return null;
        }
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * LocalDateTime --> Date
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }


    /**
     * LocalDate --> Date
     */
    public static Date localDateToDate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 字符串转LocalDate
     *   格式判断，如果8位按 yyyyMMdd 18位长度 yyyy-MM-dd
     * @param dateStr 日期字符串
     * @return 返回LocalDate
     */
    public static LocalDate stringToLocalDate(String dateStr) {
        if (StringUtils.isNotBlank(dateStr) && dateStr.length() == DATE_LEN_8) {
            return stringToLocalDate(dateStr, SIMPLE_DATE);
        }
        return stringToLocalDate(dateStr, HYPHEN_DATE);
    }

    public static LocalDate stringToLocalDate(String dateStr, String pattern) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    public static String localDateFormat(LocalDate localDate, String pattern) {
        DateTimeFormatter dateformat = DateTimeFormatter.ofPattern(null == pattern ? HYPHEN_DATE : pattern);
        if (null != localDate) {
            return localDate.format(dateformat);
        }
        return null;
    }
}
