//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * @author: 刘琪
 * @describe: 日期，时间工具
 * class_name: DateUtils
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date: 2017/12/25  21:03
 **/
public class DateUtils {


    /**************时间，日期格式**************/
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";


    /**
     * 默认日期格式
     **/
    private static final SimpleDateFormat date_format = new SimpleDateFormat(DATE_PATTERN);

    /**
     * 日期，时间格式
     **/
    private static final SimpleDateFormat datetime_format = new SimpleDateFormat(DATETIME_PATTERN);

    /**
     * @method_name: dateFormat
     * @describe: 将时间格式转为制定格式的字符串
     * @param [date:带转换的时间, pattern: 需要转换的格式{"yyyy-MM-dd"，"yyyy-MM-dd HH:mm:ss"，"HH:mm:ss"}]
     * @return java.lang.String
     * @author 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2017/12/26  21:45
     **/
    public static String dateFormat(Date date, String pattern) {
        if (date == null) {
            throw new RRException("日期参数不能为空");
        } else {
            return new SimpleDateFormat(pattern).format(date);
        }
    }

    /**
     * @method_name: getPreMonthFirstDay
     * @describe: 获取上一个月的第一天
     * @param []
     * @return java.lang.String
     * @author 刘琪
     * @QQ; 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2017/12/27  9:09
     **/
    public static String getPreMonthFirstDay() {

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return datetime_format.format(c.getTime());
    }

    /**
     * @method_name: getPreMonthLastDay
     * @describe: 获取上一个月的最后一天
     * @param []
     * @return java.lang.String
     * @author 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2017/12/27  9:10
     **/
    public static String getPreMonthLastDay() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 0);
        return datetime_format.format(c.getTime());
    }

    /**
     * @method_name: getCurrentMonthFirstDay
     * @describe: 获取当前月第一天
     * @param []
     * @return java.lang.String
     * @author 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2017/12/27  9:10
     **/
    public static String getCurrentMonthFirstDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return datetime_format.format(c.getTime());
    }

    /**
     * @method_name: getCurrentMonthLastDay
     * @describe: 获取当前月最后一天
     * @param []
     * @return java.lang.String
     * @author 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2017/12/27  9:10
     **/
    public static String getCurrentMonthLastDay() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return datetime_format.format(c.getTime());
    }

    /**
     * @method_name: dateAddOrSubtractNumberYear
     * @describe: 获取给定日期的  前/后 number 年的日期
     * @param: [date：给定的日期, number：具体推移的数值，可以时正数，正数表示往后的时间；也可以时负数，负数表示往前的时间]
     * @return java.util.Date
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2017/12/27  16:32
     **/
    public static Date dateAddOrSubtractNumberYear(Date date,int number) {
        Calendar c_ = getCalendarByDate(date);
        c_.set(Calendar.YEAR, c_.get(Calendar.YEAR) + number);
        return c_.getTime();
    }

    /**
     * @method_name: dateAddOrSubtractNumberMonth
     * @describe: 获取给定日期的 前/后 number 个月的日期
     * @param: [date：给定的日期, number：具体推移的数值，可以时正数，正数表示往后的时间；也可以时负数，负数表示往前的时间]
     * @return java.util.Date
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2017/12/27  16:32
     **/
    public static Date dateAddOrSubtractNumberMonth(Date date,int number) {
        Calendar c_ = getCalendarByDate(date);
        c_.set(Calendar.MONTH, c_.get(Calendar.MONTH) + number);
        return c_.getTime();
    }

    /**
     * @method_name: dateAddOrSubtractNumberDays
     * @describe: 获取给定日期的 前/后 number 天的日期
     * @param: [date：给定的日期, number：具体推移的数值，可以时正数，正数表示往后的时间；也可以时负数，负数表示往前的时间]
     * @return java.util.Date
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2017/12/27  16:32
     **/
    public static Date dateAddOrSubtractNumberDays(Date date,int number) {
        Calendar c_ = getCalendarByDate(date);
        c_.set(Calendar.DATE, c_.get(Calendar.DATE) + number);
        return c_.getTime();
    }

    /**
     * @method_name: dateAddOrSubtractNumberHours
     * @describe: 获取给定日期的 前/后 number 小时的日期
     * @param: [date：给定的日期, number：具体推移的数值，可以时正数，正数表示往后的时间；也可以时负数，负数表示往前的时间]
     * @return java.util.Date
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2017/12/27  16:32
     **/
    public static Date dateAddOrSubtractNumberHours(Date date,int number) {
        Calendar c_ = getCalendarByDate(date);
        c_.set(Calendar.HOUR, c_.get(Calendar.HOUR) + number);
        return c_.getTime();
    }
    /**
     * @method_name: dateAddOrSubtractNumberMinutes
     * @describe: 获取给定日期的 前/后 number 分钟的日期
     * @param: [date：给定的日期, number：具体推移的数值，可以时正数，正数表示往后的时间；也可以时负数，负数表示往前的时间]
     * @return java.util.Date
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2017/12/27  16:32
     **/
    public static Date dateAddOrSubtractNumberMinutes(Date date,int number) {
        Calendar c_ = getCalendarByDate(date);
        c_.set(Calendar.MINUTE, c_.get(Calendar.MINUTE) + number);
        return c_.getTime();
    }
    /**
     * @method_name: dateAddOrSubtractNumberSecodes
     * @describe: 获取给定日期的 前/后 number 秒的日期
     * @param: [date：给定的日期, number：具体推移的数值，可以时正数，正数表示往后的时间；也可以时负数，负数表示往前的时间]
     * @return java.util.Date
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2017/12/27  16:32
     **/
    public static Date dateAddOrSubtractNumberSecodes(Date date,int number) {
        Calendar c_ = getCalendarByDate(date);
        c_.set(Calendar.SECOND, c_.get(Calendar.SECOND) + number);
        return c_.getTime();
    }

    /**
     * @method_name: getDaysOfYear
     * @describe: 获取当前日期所在年份总天数
     * @param [date：当前日期]
     * @return int
     * @author 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2017/12/27  11:40
     **/
    public static int getDaysOfYear(Date date){
        Calendar c_ = getCalendarByDate(date);
        return c_.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    /**
     * @method_name: getDaysOfMonth
     * @describe: 获取给定时间所在月份的总天数
     * @param: [date]
     * @return int
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2017/12/27  14:54
     **/
    public static int getDaysOfMonth(Date date){
        Calendar c_ = getCalendarByDate(date);
        return c_.getActualMaximum(Calendar.DATE);
    }


    /**
     * @method_name: maxDateOfMonth
     * @describe: 获取给定时间2017-12-20所在的当前月的最大的一天的日期 如：2017-12-31
     * @param [date：时间2017-12-20 获取时间2017-12-20 12：22：30]
     * @return java.util.Date
     * @author 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2017/12/27  12:53
     **/
    public static Date getMaxDateOfMonth(Date date) throws ParseException {
        Calendar c_ = getCalendarByDate(date);
        int value = c_.getActualMaximum(Calendar.DATE);
        return datetime_format.parse(dateFormat(date,"yyyy-MM")+"-"+value);
    }

    /**
     * @method_name: getMinDateOfMonth
     * @describe: 获取给定时间2017-12-20所在的当前月的最小的一天的日期 如：2017-12-01
     * @param [date：时间2017-12-20 获取时间2017-12-20 12：22：30]
     * @return java.util.Date
     * @author 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2017/12/27  12:55
     **/
    public static Date getMinDateOfMonth(Date date) throws ParseException {
        Calendar c_ = getCalendarByDate(date);
        int value = c_.getActualMinimum(Calendar.DATE);
        return datetime_format.parse(dateFormat(date,"yyyy-MM")+"-"+value);
    }

    /**
     * @method_name: getNowMonth
     * @describe: 获取给定时间所在的月份数
     * @param [date： 如：2017-12-20]
     * @return int
     * @author 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2017/12/27  13:02
     **/
    public static int getNowMonth(Date date){
        Calendar c_ = getCalendarByDate(date);
        return c_.get(Calendar.MONTH) + 1;
    }
    
    /**
     * @method_name: getNowYear
     * @describe: 获取给定时间所在的年份数
     * @param [date： 如：2017-12-20]
     * @return int  如：2017
     * @author 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2017/12/27  13:06
     **/
    public static int getNowYear(Date date){
        Calendar c_ = getCalendarByDate(date);
        return c_.get(Calendar.YEAR);
    }


    /**
     * @method_name: getWeekStartDate
     * @describe: 获取指定日期所在的这个星期的星期一的日期
     * @param: [date]
     * @return java.util.Date
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2017/12/27  13:43
     **/
    public static Date getWeekStartDate(Date date){
        Calendar cal = getCalendarByDate(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    /**
     * @method_name: getDayOfWeek
     * @describe: 返回给定日期是一个星期中的星期几
     * @param: [date]
     * @return java.lang.String
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2017/12/27  14:06
     **/
    public static String getDayOfWeek(Date date){
        Calendar cal = getCalendarByDate(date);
        String result = "";
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                result = "星期日";
                break;
            case 2:
                result = "星期一";
                break;
            case 3:
                result = "星期二";
                break;
            case 4:
                result = "星期三";
                break;
            case 5:
                result = "星期四";
                break;
            case 6:
                result = "星期五";
                break;
            case 7:
                result = "星期六";
                break;
        }
        return result;
    }


    /**
     * @method_name: dateCompare
     * @describe: 两个日期比较大小 如果date>compareDate,返回1；相等返回0；小于返回-1
     * @param [date：被比较日期对象, compareDate：要比较的日期对象]
     * @return int
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date: 2017/12/27  10:58
     **/
    public static int dateCompare(Date date,Date compareDate){
        Calendar c1_ = getCalendarByDate(date);//被比较日期对象
        Calendar c2_ = getCalendarByDate(compareDate);//要比较的日期对象
        return c1_.compareTo(c2_);
    }


    /**
     * @method_name: dateBetween
     * @describe: 获取两个日期之间的天数（不计算时分秒在内）
     * @param [startDate, endDate]
     * @return int
     * @author 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2017/12/27  11:07
     **/
    public static int dateBetween(Date startDate,Date endDate) throws ParseException {
        if (startDate == null || endDate == null){
            throw new RRException("日期参数不能为空");
        }
        Date _startDate = date_format.parse(date_format.format(startDate));
        Date _endDate = date_format.parse(date_format.format(endDate));
        return (int)((_endDate.getTime() - _startDate.getTime())/1000/60/60/24) + 1;
    }

///////////////////////////////////////////下面不再写方法////////////////////////////////////////////////////////////
    /**
     * @method_name: getCalendarByDate
     * @describe: 获取给定日期时间的Calendr对象；
     *              私有方法，内部方法使用多个方法调用，避免写重复代码
     * @param: [date]
     * @return java.util.Calendar
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2017/12/27  14:23
     **/
    private static Calendar getCalendarByDate(Date date){
        if (date == null){
            throw new RRException("方法getCalendarByDate调用时异常，日期参数不能为空");
        }
        Calendar c_ = Calendar.getInstance();
        c_.clear();//注：在使用set方法之前，必须先clear一下，否则很多信息会继承自系统当前时间
        c_.setTime(date);
        return c_;
    }
    
    /**
     * 计算第二个日期与第一个日期相差的自然月
     * @作者：邓召仕
     * 2018年7月27日
     * @param startDate 第一日期
     * @param endDate 第二日期
     * @return
     */
    public static int calDiffMonth(Date startDate, Date endDate) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(startDate);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(endDate);

		int startYear = c1.get(Calendar.YEAR);
		int startMonth = c1.get(Calendar.MONTH) + 1;
		int startDay = c1.get(Calendar.DAY_OF_MONTH);

		int endYear = c2.get(Calendar.YEAR);
		int endMonth = c2.get(Calendar.MONTH) + 1;
		int endDay = c2.get(Calendar.DAY_OF_MONTH);

		int yearToMonth = (endYear - startYear) * 12;
		int monthToMonth = 0;
		if (endDay >= startDay) {
			monthToMonth = endMonth - startMonth;
		} else {
			monthToMonth = endMonth - startMonth - 1;
		}
		return yearToMonth + monthToMonth;
	}
	/** 
	* @Description: 计算两个时间相差的分钟数
	* @Param: [date1, data2] 
	* @return: int 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/9/23 15:38
	* @Tel  17328595627
	*/ 
	public static long dateSubtract(Date startDate,Date endDate){
       return   Math.abs( startDate.getTime() - endDate.getTime()) / (1000 * 60);
    }
    /** 
    * @Description: 根据传入年和月份，计算月份的最后一天 
    * @Param: [year, month] 
    * @return: java.lang.String 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/29 10:15
    * @Tel  17328595627
    */ 
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }
    /**
     * @Description: 根据传入年和月份，计算月份的第一天
     * @Param: [year, month]
     * @return: java.lang.String
     * @Author: 田金海
     * @Email: 895101047@qq.com
     * @Date: 2018/9/29 10:15
     * @Tel  17328595627
     */
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }
}
