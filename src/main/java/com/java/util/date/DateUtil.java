package com.java.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * 日期工具类
 * 
 * @author gxing
 *
 */
public class DateUtil {

	public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_DATETIME_ID = "yyyyMMddHHmmssSSS";
	public static final String FORMAT_DATE = "yyyy-MM-dd";
	public static final String FORMAT_DATE_ID = "yyyyMMdd";
	public static final String FORMAT_YEAR_MONTH = "yyyyMM";
	public static final String FORMAT_TIME = "HH:mm:ss";
	public static final String FORMAT_TIME_MINUTE = "yyyy-MM-dd HH:mm";
	public static final String[] WEEKOFDAYS = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

	private static final int[] DAY_OF_MONTH = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	/**
	 * 
	 * @param strDate
	 *            输入日期
	 * @param dayNum
	 *            相隔天数 正整数表示前推 ，负整数表示后推
	 * @return 日期字符串
	 * @throws ParseException
	 */
	public static String getDifferDate(String strDate, int dayNum) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sf.parse(strDate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, dayNum);
		String sqldate = sf.format(c.getTime());
		return sqldate;
	}

	/**
	 * 取得当前日期相隔dayNum天的日期
	 * 
	 * @param strDate
	 * @param dayNum
	 * @return
	 * @throws ParseException
	 */
	public static String getDifferDate(int dayNum) throws ParseException {
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, dayNum);
		String strDate = sf.format(c.getTime());
		return strDate;
	}

	/**
	 * 校验日期YYYY-MM-DD格式是否正确
	 * 
	 * @param date
	 * @return
	 */
	public static boolean checkDateForm(String date) {
		if (date == null || "".equals(date))
			return false;
		String eL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		return Pattern.compile(eL).matcher(date).matches();
	}

	/**
	 * 取得指定天数后的时间
	 * 
	 * @param date
	 *            基准时间
	 * @param dayAmount
	 *            指定天数，允许为负数
	 * @return 指定天数后的时间
	 */
	public static Date addDay(Date date, int dayAmount) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, dayAmount);
		return calendar.getTime();
	}

	/**
	 * 取得指定小时数后的时间
	 * 
	 * @param date
	 *            基准时间
	 * @param hourAmount
	 *            指定小时数，允许为负数
	 * @return 指定小时数后的时间
	 */
	public static Date addHour(Date date, int hourAmount) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, hourAmount);
		return calendar.getTime();
	}

	/**
	 * 取得指定分钟数后的时间
	 * 
	 * @param date
	 *            基准时间
	 * @param minuteAmount
	 *            指定分钟数，允许为负数
	 * @return 指定分钟数后的时间
	 */
	public static Date addMinute(Date date, int minuteAmount) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minuteAmount);
		return calendar.getTime();
	}

	/**
	 * 比较两日期对象中的小时和分钟部分的大小.
	 * 
	 * @param date
	 *            日期对象1, 如果为 <code>null</code> 会以当前时间的日期对象代替
	 * @param anotherDate
	 *            日期对象2, 如果为 <code>null</code> 会以当前时间的日期对象代替
	 * @return 如果日期对象1大于日期对象2, 则返回大于0的数; 反之返回小于0的数; 如果两日期对象相等, 则返回0.
	 */
	public static int compareHourAndMinute(Date date, Date anotherDate) {
		if (date == null) {
			date = new Date();
		}

		if (anotherDate == null) {
			anotherDate = new Date();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hourOfDay1 = cal.get(Calendar.HOUR_OF_DAY);
		int minute1 = cal.get(Calendar.MINUTE);

		cal.setTime(anotherDate);
		int hourOfDay2 = cal.get(Calendar.HOUR_OF_DAY);
		int minute2 = cal.get(Calendar.MINUTE);

		if (hourOfDay1 > hourOfDay2) {
			return 1;
		} else if (hourOfDay1 == hourOfDay2) {
			// 小时相等就比较分钟
			return minute1 > minute2 ? 1 : (minute1 == minute2 ? 0 : -1);
		} else {
			return -1;
		}
	}

	/**
	 * 比较两日期对象的大小, 忽略秒, 只精确到分钟.
	 * 
	 * @param date
	 *            日期对象1, 如果为 <code>null</code> 会以当前时间的日期对象代替
	 * @param anotherDate
	 *            日期对象2, 如果为 <code>null</code> 会以当前时间的日期对象代替
	 * @return 如果日期对象1大于日期对象2, 则返回大于0的数; 反之返回小于0的数; 如果两日期对象相等, 则返回0.
	 */
	public static int compareIgnoreSecond(Date date, Date anotherDate) {
		if (date == null) {
			date = new Date();
		}

		if (anotherDate == null) {
			anotherDate = new Date();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		date = cal.getTime();

		cal.setTime(anotherDate);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		anotherDate = cal.getTime();

		return date.compareTo(anotherDate);
	}

	/**
	 * 取得当前时间的字符串表示，格式为2006-01-10 20:56:30.756
	 * 
	 * @return 当前时间的字符串表示
	 */
	public static String currentDate2String() {
		return date2String(new Date());
	}

	/**
	 * 取得当前时间的字符串表示，格式为2006-01-10
	 * 
	 * @return 当前时间的字符串表示
	 */
	public static String currentDate2StringByDay() {
		return date2StringByDay(new Date());
	}

	/**
	 * 把时间转换成字符串，格式为2006-01-10 20:56:30.756
	 * 
	 * @param date
	 *            时间
	 * @return 时间字符串
	 */
	public static String date2String(Date date) {
		return date2String(date, "yyyy-MM-dd HH:mm:ss.SSS");
	}

	/**
	 * 按照指定格式把时间转换成字符串，格式的写法类似yyyy-MM-dd HH:mm:ss.SSS
	 * 
	 * @param date
	 *            时间
	 * @param pattern
	 *            格式
	 * @return 时间字符串
	 */
	public static String date2String(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		return (new SimpleDateFormat(pattern)).format(date);
	}

	/**
	 * 把时间转换成字符串，格式为2006-01-10
	 * 
	 * @param date
	 *            时间
	 * @return 时间字符串
	 */
	public static String date2StringByDay(Date date) {
		return date2String(date, "yyyy-MM-dd");
	}

	/**
	 * 把时间转换成字符串，格式为2006-01-10 20:56
	 * 
	 * @param date
	 *            时间
	 * @return 时间字符串
	 */
	public static String date2StringByMinute(Date date) {
		return date2String(date, "yyyy-MM-dd HH:mm");
	}

	/**
	 * 把时间转换成字符串，格式为2006-01-10 20:56:30
	 * 
	 * @param date
	 *            时间
	 * @return 时间字符串
	 */
	public static String date2StringBySecond(Date date) {
		return date2String(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 字符串转时间
	 * 
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date string2Date(String str, String format) {
		if (str == null) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = dateFormat.parse(str);
		} catch (ParseException e) {
			return null;
		}
		return date;
	}

	/**
	 * 根据某星期几的英文名称来获取该星期几的中文数. <br>
	 * 
	 * @param englishWeekName
	 *            星期的英文名称
	 * @return 星期的中文数
	 */
	public static String getChineseWeekNumber(String englishWeekName) {
		if ("monday".equalsIgnoreCase(englishWeekName)) {
			return "一";
		}

		if ("tuesday".equalsIgnoreCase(englishWeekName)) {
			return "二";
		}

		if ("wednesday".equalsIgnoreCase(englishWeekName)) {
			return "三";
		}

		if ("thursday".equalsIgnoreCase(englishWeekName)) {
			return "四";
		}

		if ("friday".equalsIgnoreCase(englishWeekName)) {
			return "五";
		}

		if ("saturday".equalsIgnoreCase(englishWeekName)) {
			return "六";
		}

		if ("sunday".equalsIgnoreCase(englishWeekName)) {
			return "日";
		}

		return null;
	}
	
	/**
	 * 取得今天的第一个时刻
	 * 例：Sun Apr 22 00:00:00 CST 2018
	 * @return 今天的第一个时刻
	 */
	public static Date currentStartDate() {
		return getStartDate(new Date());
	}
	
	/**
	 * 取得今天的最后一个时刻
	 * 例：Sun Apr 22 23:59:59 CST 2018
	 * @return 今天的最后一个时刻
	 */
	public static Date currentEndDate() {
		return getEndDate(new Date());
	}
	
	/**
	 * 获取某天的起始时间, e.g. 2005-10-01 00:00:00.000
	 * 
	 * @param date
	 *            日期对象
	 * @return 该天的起始时间
	 */
	public static Date getStartDate(Date date) {
		if (date == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}
	
	/**
	 * 获取某天的结束时间, e.g. 2005-10-01 23:59:59.999
	 * 
	 * @param date
	 *            日期对象
	 * @return 该天的结束时间
	 */
	public static Date getEndDate(Date date) {

		if (date == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);

		return cal.getTime();
	}
	
	/**
	 * 取得某个日期是星期几，星期日是1，依此类推
	 * 
	 * @param date
	 *            日期
	 * @return 星期几
	 */
	public static int getDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	
	/**
	 * 把日期字符串转换为util.date类型
	 * 
	 * @param strDate
	 *            日期字符串(yyyy-MM-dd)
	 * @return date 类型, yyyy-MM-dd格式
	 * @throws ParseException
	 */
	public static Date getDate(String strDate) throws ParseException {
		return java.sql.Date.valueOf(strDate);
	}

	/**
	 * 根据指定的年, 月, 日等参数获取日期对象.
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param date
	 *            日
	 * @return 对应的日期对象
	 */
	public static Date getDate(int year, int month, int date) {
		return getDate(year, month, date, 0, 0);
	}

	/**
	 * 根据指定的年, 月, 日, 时, 分等参数获取日期对象.
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param date
	 *            日
	 * @param hourOfDay
	 *            时(24小时制)
	 * @param minute
	 *            分
	 * @return 对应的日期对象
	 */
	public static Date getDate(int year, int month, int date, int hourOfDay, int minute) {
		return getDate(year, month, date, hourOfDay, minute, 0);
	}

	/**
	 * 根据指定的年, 月, 日, 时, 分, 秒等参数获取日期对象.
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param date
	 *            日
	 * @param hourOfDay
	 *            时(24小时制)
	 * @param minute
	 *            分
	 * @param second
	 *            秒
	 * @return 对应的日期对象
	 */
	public static Date getDate(int year, int month, int date, int hourOfDay, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, date, hourOfDay, minute, second);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	/**
	 * 取得一个月最大的的日期
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            月份，0表示1月，依此类推
	 * @return 最多的天数
	 */
	public static int getMaxDayOfMonth(int year, int month) {
		if (month == 1 && isLeapYear(year)) {
			return 29;
		}
		return DAY_OF_MONTH[month];
	}

	/**
	 * 得到指定日期的下一天
	 * 
	 * @param date
	 *            日期对象
	 * @return 同一时间的下一天的日期对象
	 */
	public static Date getNextDay(Date date) {
		return addDay(date, 1);
	}

	

	/**
	 * 根据日期对象来获取日期中的时间(HH:mm:ss).
	 * 
	 * @param date
	 *            日期对象
	 * @return 时间字符串, 格式为: HH:mm:ss
	 */
	public static String getTime(Date date) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		return format.format(date);
	}

	/**
	 * 根据日期对象来获取日期中的时间(HH:mm).
	 * 
	 * @param date
	 *            日期对象
	 * @return 时间字符串, 格式为: HH:mm
	 */
	public static String getTimeIgnoreSecond(Date date) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(date);
	}

	/**
	 * 判断是否是闰年
	 * 
	 * @param year
	 *            年份
	 * @return 是true，否则false
	 */
	public static boolean isLeapYear(int year) {
		Calendar calendar = Calendar.getInstance();
		return ((GregorianCalendar) calendar).isLeapYear(year);
	}

	/**
	 * 取得一年中的第几周。
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取上周的指定星期的日期。
	 * 星期日为一周的第一天
	 * @param dayOfWeek
	 *            星期几，取值范围是 {@link Calendar#MONDAY} - {@link Calendar#SUNDAY}
	 */
	public static Date getDateOfPreviousWeek(int dayOfWeek) {
		if (dayOfWeek > 7 || dayOfWeek < 1) {
			throw new IllegalArgumentException("参数必须是1-7之间的数字");
		}

		return getDateOfRange(dayOfWeek, -7);
	}

	/**
	 * 获取本周的指定星期的日期。
	 * 星期日为一周的第一天
	 * @param dayOfWeek
	 *            星期几，取值范围是 {@link Calendar#MONDAY} - {@link Calendar#SUNDAY}
	 */
	public static Date getDateOfCurrentWeek(int dayOfWeek) {
		if (dayOfWeek > 7 || dayOfWeek < 1) {
			throw new IllegalArgumentException("参数必须是1-7之间的数字");
		}

		return getDateOfRange(dayOfWeek, 0);
	}

	/**
	 * 获取下周的指定星期的日期。
	 * 
	 * @param dayOfWeek
	 *            星期几，取值范围是 {@link Calendar#MONDAY} - {@link Calendar#SUNDAY}
	 */
	public static Date getDateOfNextWeek(int dayOfWeek) {
		if (dayOfWeek > 7 || dayOfWeek < 1) {
			throw new IllegalArgumentException("参数必须是1-7之间的数字");
		}

		return getDateOfRange(dayOfWeek, 7);
	}

	private static Date getDateOfRange(int dayOfWeek, int dayOfRange) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + dayOfRange);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	
	/**
	 * 20150728125030123
	 * 
	 * @return
	 */
	public static String getCurrentDateTimeID() {
		return DateFormatUtils.format(System.currentTimeMillis(), FORMAT_DATETIME_ID);
	}

	/**
	 * 2015-07-28 12:50:30
	 * 
	 * @return
	 */
	public static String getCurrentDateTime() {
		return getCurrentDateTimeWithDelay(0);
	}

	/**
	 * 2015-07-28 12:50:30
	 * 
	 * @param milliseconds
	 * @return
	 */
	public static String getCurrentDateTimeWithDelay(int milliseconds) {
		return DateFormatUtils.format(System.currentTimeMillis() + milliseconds, FORMAT_DATETIME);
	}

	/**
	 * 2015-07-28
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		return DateFormatUtils.format(System.currentTimeMillis(), FORMAT_DATE);
	}

	/**
	 * 
	 * @return 20150728
	 */
	public static String getCurrentDateID() {
		return DateFormatUtils.format(System.currentTimeMillis(), FORMAT_DATE_ID);
	}

	/**
	 * 
	 * @return 201507
	 */
	public static String getCurrentYearMonth() {
		return DateFormatUtils.format(System.currentTimeMillis(), FORMAT_YEAR_MONTH);
	}

	/**
	 * 12:50:30
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		return DateFormatUtils.format(System.currentTimeMillis(), FORMAT_TIME);
	}
	
	/**
	 * 今天以前或之后 相差 deltMillisecond 毫秒的日期
	 * @param deltYear
	 * @return String
	 */
	public static String getDateTimeSinceNow(int deltMillisecond) {
		return DateFormatUtils.format(DateUtils.addMilliseconds(new Date(), deltMillisecond).getTime(),
				FORMAT_DATETIME);
	}

	/**
	 * 今天以前或之后 相差 deltDay 天的日期
	 * @param deltDay 可以是正负数，
	 * @return String
	 */
	public static String getDateSinceToday(int deltDay) {
		return DateFormatUtils.format(DateUtils.addDays(new Date(), deltDay).getTime(), FORMAT_DATE);
	}
	
	/**
	 * 今天以前或之后 相差 month 月的日期
	 * @param month
	 * @return String
	 */
	public static String getDateSinceTodayByMonth(int month) {
		return DateFormatUtils.format(DateUtils.addMonths(new Date(), month), FORMAT_DATE);
	}

	/**
	 * 今天以前或之后 相差 deltYear 年的日期
	 * @param deltYear
	 * @return String
	 */
	public static String getYearSinceToday(int deltYear) {
		return DateFormatUtils.format(DateUtils.addYears(new Date(), deltYear).getTime(), FORMAT_DATE);
	}

	/**
	 * @Title:getCurrentMonthFirstDay
	 * @Description: 得到当前月的第一天.
	 * @return
	 * @return String
	 */
	public static String getCurrentMonthFirstDay() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		return DateFormatUtils.format(cal, FORMAT_DATE);

	}

	/**
	 * 获取当年的第一天
	 * 
	 * @param year
	 * @return
	 */
	public static Date getCurrYearFirst() {
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}

	/**
	 * 获取某年第一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static Date getYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 获取本周第一天
	 * 
	 * @return
	 */
	public static Date getNowWeekBegin() {
		int mondayPlus;
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK); // 如果按中国礼拜一作为第一天所以这里需减1
		mondayPlus = 1 - dayOfWeek;

		if (dayOfWeek == 1) {
			mondayPlus = 0;
		} else {
			mondayPlus = 1 - dayOfWeek;
		}

		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();

		return monday;

	}

	/**
	 * 拼接最大最间：HH:mm:ss
	 * @param dateStr
	 * @return String
	 */
	public static String appendMaxTimeStr(String dateStr) {
		if (StringUtils.isNotBlank(dateStr) && dateStr.length() == 10) {
			// 加上本日最大时间信息，避免sql语句用date函数来获取日期信息，可提高查询效率
			dateStr += " 23:59:59";
		}
		return dateStr;
	}

	/**
	 * 输入日期，与当前日期比较，0表示同一天，1示表比当前日期更老，-1表示比当前日期更新
	 * @param date
	 * @param timeFormater
	 *            默认比较 年月日，为了提高公用性， type 可传 HH:mm:ss 用于比较 时分秒
	 * @return 
	 */
	public static int compareDate(String date, String timeFormater) {
		String s1 = DateUtil.getCurrentDate();
		if (!timeFormater.equals("HH:mm:ss")) {
			timeFormater = "";
		}
		java.text.DateFormat df = new java.text.SimpleDateFormat(FORMAT_DATE + timeFormater);
		java.util.Calendar c1 = java.util.Calendar.getInstance();
		java.util.Calendar c2 = java.util.Calendar.getInstance();
		try {
			c1.setTime(df.parse(s1));
			c2.setTime(df.parse(date));
		} catch (java.text.ParseException e) {
			return -1;
		}
		int result = c1.compareTo(c2);
		return result;
	}

	/**
	 * 输入日期，与当前日期比较，0表示同一天，1示表比当前日期更老，-1表示比当前日期更新
	 * @param date
	 * @param timeFormater
	 *            默认比较 年月日，为了提高公用性， type 可传 HH:mm:ss 用于比较 时分秒
	 * @return 
	 */
	public static int compareDate(String date) {
		return compareDate(date, "");
	}

	/**
	 * 获取指定日期是星期几 参数为null时表示获取当前日期是星期几
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getWeekOfDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		Date date1 = null;
		try {
			date1 = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date1);
		}
		int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return WEEKOFDAYS[w];
	}

	/**
	 * 取得当月天数(最后的日期)
	 */
	public static int getCurrentMonthLastDay() {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 上一个月
	 * 
	 * @return
	 */
	public static String getLastMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		return sdf.format(getLastDate(new Date()));
	}

	/**
	 * 根据传入日期，计算上个月同一天的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 比较两个日期的大小：yyyy-MM-dd
	 * 
	 * @throws ParseException
	 */
	public static boolean compareTowDateSize(String minDate, String maxDate) throws ParseException {
		boolean bool = false;
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		try {
			if (sdf.parse(minDate).getTime() - sdf.parse(maxDate).getTime() <= 0) {
				bool = true;
			}
		} catch (ParseException e) {
			bool = false;
		}
		return bool;
	}

	/**
	 * 比较两个时间的大小:yyyy-MM-dd HH:mm
	 * 
	 * @throws ParseException
	 */
	public static boolean compareTowDateSize2(String minDate, String maxDate) {
		boolean bool = false;
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TIME_MINUTE);
		try {
			if (sdf.parse(minDate).getTime() - sdf.parse(maxDate).getTime() <= 0) {
				bool = true;
			}
		} catch (ParseException e) {
			bool = false;
		}
		return bool;
	}

	

	/**
	 * 收集起始时间到结束时间之间所有的时间并以字符串集合方式返回
	 * 
	 * @param timeStart
	 * @param timeEnd
	 * @return
	 */
	public static List<String> collectLocalDates(String timeStart, String timeEnd) {
		return collectLocalDates(LocalDate.parse(timeStart), LocalDate.parse(timeEnd));
	}

	/**
	 * 收集起始时间到结束时间之间所有的时间并以字符串集合方式返回
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<String> collectLocalDates(LocalDate start, LocalDate end) {
		return Stream.iterate(start, localDate -> localDate.plusDays(1)).limit(ChronoUnit.DAYS.between(start, end) + 1)
				.map(LocalDate::toString).collect(Collectors.toList());
	}

	/**
	 * 判断今天是不是周五
	 * @return boolean
	 */
	public static boolean geTodayIsFriday() {
		// 周五是一个星期的第6天，0-5
		int friday = 5;
		// 周五是一个星期的第6天
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (week == friday) {
			return true;
		}
		return false;
	}
}
