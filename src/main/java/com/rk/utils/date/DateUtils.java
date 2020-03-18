package com.rk.utils.date;

import com.rk.utils.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 处理时间的工具类
 *
 * @author zk
 */
public class DateUtils {
    static Logger log = LoggerFactory.getLogger(DateUtils.class);

    /**
     * 获取形如yyyyMMddHHmmss的当前日期字串
     *
     * @return String
     */
    public static String getDateTimeString() {
        // 日期格式
        DateFormat dfmt = new SimpleDateFormat("yyyyMMddHHmmss");
        return dfmt.format(new Date());
    }

    /**
     * 获取形如yyyy-MM-dd HH:mm:ss.SSS的当前日期字串
     *
     * @return String
     */
    public static String getTimestampString() {
        // 日期格式
        DateFormat dfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return dfmt.format(new Timestamp(new Date().getTime()));
    }

    /**
     * 获取形如yyyyMMdd的当前日期字串
     *
     * @return String
     */
    public static String getDateString() {
        // 日期格式
        DateFormat dfmt = new SimpleDateFormat("yyyyMMdd");
        return dfmt.format(new Date());
    }

    public static String getYYYYMMDDHH24MISS(String strYYYY_MM_DD) {
        String tmp = "";
        try {
            Date d = getDate(strYYYY_MM_DD, "yyyy-mm-dd");
            DateFormat dfmt = new SimpleDateFormat("yyyyMMddHHmmss");
            return dfmt.format(d);
        } catch (ParseException e) {
            ;
        }

        return tmp;
    }

    /**
     * 获取指定格式的当前日期字串
     *
     * @return String
     */
    public static String getDateString(String pattern) {
        // 日期格式
        DateFormat dfmt = new SimpleDateFormat(pattern);
        return dfmt.format(new Date());
    }

    /**
     * 日期运算
     *
     * @param days    和当前日期的差值（单位:天）
     * @param pattern
     * @return
     */
    public static String getDateString(int days, String pattern) {
        DateFormat dfmt = new SimpleDateFormat(pattern);
        long days2 = (long) days;
        return dfmt.format(new Date((new Date()).getTime() + 1000 * 60 * 60 * 24 * days2));
    }

    /**
     * 获取形如yyyyMMddHHmmss的日期字串
     *
     * @param date Date
     * @return String
     */
    public static String getDateString(Date date) {
        DateFormat vdfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date == null) {
            return "";
        }
        return vdfmt.format(date);
    }

    /**
     * 获取指定格式的日期字串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String getDateString(Date date, String pattern) {
        SimpleDateFormat sdfmt = new SimpleDateFormat(pattern);

        return date != null ? sdfmt.format(date) : "";
    }

    public static String getDateString(String date, String pattern) {
        if (date == null || "".equals(date) || "null".equals(date))
            return "";
        try {
            Date d = new Date(date);
            return getDateString(d, pattern);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDateString(java.sql.Date date, String pattern) {
        SimpleDateFormat sdfmt = new SimpleDateFormat(pattern);
        return date != null ? sdfmt.format(date) : "";
    }

    public static java.sql.Date getSQLDate(String date, String pattern) {
        SimpleDateFormat s = new SimpleDateFormat(pattern);
        try {
            if (StringUtils.isEmpty(date)) {
                Date d = s.parse(date);
                return new java.sql.Date(d.getTime());
            } else {
                return null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static java.sql.Date getSQLDate(String date) {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (!StringUtils.isEmpty(date)) {
                Date d = s.parse(date);
                return new java.sql.Date(d.getTime());
            } else {
                return null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 将形如yyyyMMddHHmmss的字串转换成日期
     *
     * @param strDate String
     * @return Date
     * @throws ParseException
     */
    public static Date getDate(String strDate) throws ParseException {
        // 日期格式
        DateFormat dfmt = new SimpleDateFormat("yyyyMMddHHmmss");
        return dfmt.parse(strDate);
    }

    /**
     * 将形如yyyy-MM-dd HH:mm:ss的字串转换成日期
     *
     * @param strDate String
     * @return Date
     * @throws ParseException
     */
    public static Date getDate_(String strDate) throws ParseException {
        // 日期格式
        DateFormat dfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dfmt.parse(strDate);
    }

    /**
     * 将指定格式的字串转换成日期
     *
     * @param strDate String
     * @return Date
     * @throws ParseException
     */
    public static Date getDate(String strDate, String pattern) throws ParseException {
        // 日期格式
        if (!StringUtils.isEmpty(strDate)) {
            DateFormat dfmt = new SimpleDateFormat(pattern);
            return dfmt.parse(strDate);
        } else {
            return null;
        }
    }

    /**
     * 将指定格式的字串(20061111163558)转换成指定格式的字串(2006-11-11 16:35:58)
     *
     * @param strDate String
     * @return Date
     * @throws ParseException
     */
    public static String getStringDate(String strDate) {
        if (strDate == null)
            return null;

        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = "";
        try {
            Date date = formatter1.parse(strDate);
            dateString = formatter2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString;
    }

    public static String getStringDate(String stringdate, String fpattern, String tpattern) {
        if (stringdate == null)
            return null;

        SimpleDateFormat formatter1 = new SimpleDateFormat(fpattern);
        SimpleDateFormat formatter2 = new SimpleDateFormat(tpattern);
        String dateString = "";
        try {
            Date date = formatter1.parse(stringdate.trim());
            dateString = formatter2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString;
    }

    /**
     * 得到当前月的后month_num个月的帐期 例如当前为2005-09，返回上个月的帐期，则设置month_num为-1,返回为200508
     * 例如当前为2005-09，返回下个月的帐期，则设置month_num为1,返回为200510
     * 例如当前为2006-1，返回上个月的帐期，则设置month_num为1,返回为200512
     */
    public static String getChdate(int month_num) {
        Calendar c1 = Calendar.getInstance();
        String result = "";
        c1.add(2, month_num);
        result = String.valueOf(c1.get(1));
        if ((c1.get(2) + 1) >= 10) {
            result = result + String.valueOf(c1.get(2) + 1);
        } else {
            result = result + "0" + String.valueOf(c1.get(2) + 1);
        }
        return result;
    }

    public static int getSysYear() {
        Calendar calendar = new GregorianCalendar();
        int iyear = calendar.get(Calendar.YEAR);
        return iyear;
    }

    public static int getDateOrTimeNumber(int number) {
        Calendar calendar = new GregorianCalendar();
        int num = calendar.get(number);
        return num;
    }

    public static int getSysMonth() {
        Calendar calendar = new GregorianCalendar();
        int imonth = calendar.get(Calendar.MONTH) + 1;
        return imonth;
    }

    public static String getDateOfSp(String sp) {
        String reday = "";
        int y = getSysYear();
        int m = getSysMonth();
        int d = getSysDay();
        reday = y + "" + sp;
        if (m < 10) {
            reday = reday + ("0" + m) + sp;
        } else {
            reday = reday + (m + "") + sp;
        }
        if (d < 10) {
            reday = reday + ("0" + d);
        } else {
            reday = reday + (d + "");
        }
        return reday;
    }

    public static String getDateOfFirstDay(String sp) {
        String reday = "";
        int y = getSysYear();
        int m = getSysMonth();
        reday = y + "" + sp;
        if (m < 10) {
            reday = reday + ("0" + m) + sp + "01";
        } else {
            reday = reday + (m + "") + sp + "01";
        }
        return reday;
    }

    public static int getSysDay() {
        Calendar calendar = new GregorianCalendar();
        int idate = calendar.get(Calendar.DAY_OF_MONTH);
        return idate;
    }

    public static String getDateString2() {
        String tmp = "";
        tmp = getSysYear() + "    " + getSysMonth() + "    " + getSysDay() + "    ";
        return tmp;
    }

    public static int getTwoMonthNum(String startDate, String endDate) {
        int year1 = Integer.parseInt(startDate.substring(0, 4));
        int year2 = Integer.parseInt(endDate.substring(0, 4));
        int month1 = Integer.parseInt(startDate.substring(4));
        int month2 = Integer.parseInt(endDate.substring(4));
        return Math.abs(year1 - year2) * 12 - (month1 - month2) + 1;
    }

    public static String getNextMonth(String startDate, int i) {
        int start = Integer.parseInt(startDate);
        int next = start + i;
        int year = Integer.parseInt(String.valueOf(next).substring(0, 4));
        int month = Integer.parseInt(String.valueOf(next).substring(4));
        if (month > 12) {
            year = year + 1;
            month = month - 12;
        }
        if (month < 10) {
            return year + "0" + month;
        } else {
            return year + "" + month;
        }
    }

    // 取得 某年某月的天数
    // 月的数值为 0-11
    public static int getDays(String yearMonth) {
        int[] days = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int year = Integer.parseInt(yearMonth.substring(0, 4));
        int month = Integer.parseInt(yearMonth.substring(4)) - 1;
        if (month == 1) {
            if (year % 4 == 0) {
                if (year % 100 == 0) {
                    return 28;
                } else {
                    return 29;
                }
            } else {
                return 28;
            }
        } else {
            return days[month];
        }
    }

    public static int isBetweenDays(String startDay, String endDay) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String today = formatter.format(date);
        startDay = today.substring(0, 6) + startDay;
        endDay = today.substring(0, 6) + endDay;
        if (today.compareTo(startDay) >= 0 && today.compareTo(endDay) <= 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public static boolean isDate(String dateStr, String dateFomrat) {
        // "yyyy-mm-dd","yyyyMMddHHmmss"
        boolean tmp = false;
        try {
            Date d = getDate(dateStr, dateFomrat);
            DateFormat dfmt = new SimpleDateFormat(dateFomrat);
            dfmt.format(d);
            tmp = true;
        } catch (ParseException e) {
            tmp = false;
        }
        return tmp;
    }

    public static boolean isBetweenDays(String startDay, String endDay, String dateFomrat) {
        boolean tmp = false;

        if (isDate(startDay, dateFomrat) && isDate(endDay, dateFomrat)) {
            try {
                if (getDate(startDay, dateFomrat).getTime() > (getDate(endDay, dateFomrat)).getTime()) {
                    tmp = false;
                } else
                    tmp = true;
            } catch (ParseException e) {
                ;
            }
        }

        return tmp;
    }

    public static String getYyyyMm(String theDayYyyy_mm_dd) {
        String dayYYYYMMDD = "";
        dayYYYYMMDD = StringUtils.replace(theDayYyyy_mm_dd, "-", "");
        return dayYYYYMMDD.substring(0, 6);
    }

    public static boolean isDateStr(String strDate, String pattern) {
        boolean tmp = true;

        try {
            getDate(strDate, pattern);
        } catch (ParseException e) {
            tmp = false;
        }

        return tmp;
    }

    /**
     * 获得指定日期之后若干天的日期
     */
    public static Date getAfterNDaysDate(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, days);

        return cal.getTime();
    }

    /**
     * @author hashan 计算两个日期相隔的分钟数
     */
    public static long DaysBetweenTwoDate(String firstString, String secondString) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date firstDate = null;
        Date secondDate = null;
        try {
            firstDate = df.parse(firstString);
            secondDate = df.parse(secondString);
        } catch (Exception e) {
            // 日期型字符串格式错误
        }

        long nDay = ((secondDate.getTime() - firstDate.getTime()) / (60 * 1000));
        return nDay;
    }

    /**
     * @author 计算两个日期相隔的天数
     */
    public static long daysBetweenTwoDate(Date date1, Date date2) {

        long nDay = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) > 0 ? (date1.getTime() - date2
                .getTime()) / (24 * 60 * 60 * 1000) : (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);

        return nDay;
    }

    /**
     * 取某jdbc的系统日期
     *
     * @return
     * @throws ParseException
     */
    public static Date getSysdateByJdbc() {
        // 日期格式
        // String SQL="SELECT TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') FROM DUAL";
        String yyyyMMddHHmmss = "";
        try {
            // /////////////////////////yyyyMMddHHmmss =
            // CommUtil.getString(SQL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!yyyyMMddHHmmss.equals("")) {
            DateFormat dfmt = new SimpleDateFormat("yyyyMMddHHmmss");
            try {
                return dfmt.parse(yyyyMMddHHmmss);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        } else
            return null;
    }

    public static Date addYMD(Date date, int yearNum, int monthNum, int dayNum) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, yearNum);
        c.add(Calendar.MONTH, monthNum);
        c.add(Calendar.DAY_OF_MONTH, dayNum);
        return c.getTime();
    }

    /**
     * 日期按天数增加
     *
     * @param endtime 日期字符串
     * @param n       增加天数，减少传负数
     * @return
     */
    public static String getDayIncrease(String endtime, int n) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date todate;
        try {
            todate = sdf.parse(endtime);
            Calendar gc = Calendar.getInstance();
            gc.setTime(todate);
            gc.add(Calendar.DAY_OF_MONTH, n);
            endtime = sdf.format(gc.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return endtime;
    }

    public static void main(String[] args) {
        log.info(DateUtils.getDayIncrease("2011-08-29", 1));
    }

    public static String getDateString(String startTime, String oldPattern, String newPattern) {
        String newDate = null;
        SimpleDateFormat sdf_old = new SimpleDateFormat(oldPattern);
        SimpleDateFormat sdf_new = new SimpleDateFormat(newPattern);
        try {
            newDate = sdf_new.format(sdf_old.parse(startTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    public static boolean sameDateField(Date d1, Date d2, int field) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        if (c1.get(field) - c2.get(field) == 0) {
            return true;
        }
        return false;
    }

    public static boolean sameDate(Date d1, Date d2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        int n1 = Integer.parseInt(sdf.format(d1));
        int n2 = Integer.parseInt(sdf.format(d2));
        if (n1 == n2) {
            return true;
        }
        return false;
    }
}
