package org.example.utils;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.exception.BasicException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by pcq on 2017/9/19.
 */
@Slf4j
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_TIME_FORMAT_2 = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String MONTH_FORMAT = "yyyy-MM";

    public static final String MONTH_FORMAT_2 = "yyyyMM";

    public static final String MONTH_FORMAT_3 = "yyyy/MM";

    public static final String MONTH_FORMAT_4 = "yyyyMMM";

    public static final String MONTH_FORMAT_5 = "yyyyMMMd";


    public static Date setTime(Date date, int hours, int minutes, int seconds, int milliseconds) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else {
            Calendar c = Calendar.getInstance();
            c.setLenient(false);
            c.setTime(date);
            c.set(Calendar.HOUR_OF_DAY, hours);
            c.set(Calendar.MINUTE, minutes);
            c.set(Calendar.SECOND, seconds);
            c.set(Calendar.MILLISECOND, milliseconds);
            return c.getTime();
        }
    }

    /**
     * 动态调整日期
     *
     * @return
     */
    public static Date shiftDate(int shiftDate) {
        Calendar calendar = Calendar.getInstance(); //得到日历
        if (shiftDate == 0) {
            return new Date();  //取自然日
        } else if (shiftDate < 0) {
            calendar.add(Calendar.DATE, shiftDate); //往前推天数，-1,-2天
            Date theDate = calendar.getTime();
            return theDate;
        } else {
            //固定每月1号
            calendar.set(Calendar.DAY_OF_MONTH, shiftDate);
            Date theDate = calendar.getTime();
            return theDate;
        }
    }

    /**
     * 动态调整日期
     *
     * @return
     */
    public static Date shiftDate(Calendar calendar, int shiftDate) {
        //Calendar calendar = Calendar.getInstance(); //得到日历
        if (shiftDate == 0) {
            return new Date();  //取自然日
        } else if (shiftDate < 0) {
            calendar.add(Calendar.DATE, shiftDate); //往前推天数，-1,-2天
            Date theDate = calendar.getTime();
            return theDate;
        } else {
            //固定每月1号
            calendar.set(Calendar.DAY_OF_MONTH, shiftDate);
            Date theDate = calendar.getTime();
            return theDate;
        }
    }

    /**
     * 获取上个月Date日期
     *
     * @param date
     * @return
     */
    public static Date getLastMonthDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        return m;
    }

    /**
     * 获取上个月日期，默认yyyy-MM格式
     *
     * @param pattern
     * @return
     */
    public static String getLastMonth(String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = MONTH_FORMAT;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -1);

        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
        String lastMonth = sdf.format(cal.getTime());
        return lastMonth;
    }

    /**
     * 获取指定月份
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date getAppointMonthDate(Date date, int amount) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, amount);
        Date m = c.getTime();
        return m;
    }

    /**
     * 取得月第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 取得月最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 取得月第一天
     *
     * @param month
     * @param monthPattern
     * @return
     * @throws ParseException
     */
    public static Date getFirstDateOfMonth(String month, String monthPattern) throws ParseException {
        SimpleDateFormat monthFormat = new SimpleDateFormat(monthPattern);
        Calendar c = Calendar.getInstance();
        c.setTime(monthFormat.parse(month));
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 取得月最后一天
     *
     * @param month
     * @param monthPattern
     * @return
     * @throws ParseException
     */
    public static Date getLastDateOfMonth(String month, String monthPattern) throws ParseException {
        SimpleDateFormat monthFormat = new SimpleDateFormat(monthPattern);
        Calendar c = Calendar.getInstance();
        c.setTime(monthFormat.parse(month));
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 获取指定日期间的所有日期字符串
     *
     * @param dBeginStr
     * @param dEndStr
     * @param pattern
     * @return
     */
    public static List<String> getBetweenDateStrList(String dBeginStr, String dEndStr, String pattern) {
        List<String> dateList = new ArrayList();

        try {
            dateList.add(dBeginStr);
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date dBegin = sdf.parse(dBeginStr);
            Date dEnd = sdf.parse(dEndStr);

            Calendar calBegin = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calBegin.setTime(dBegin);
            Calendar calEnd = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calEnd.setTime(dEnd);
            // 测试此日期是否在指定日期之后
            while (dEnd.after(calBegin.getTime())) {
                // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
                calBegin.add(Calendar.DAY_OF_MONTH, 1);
                dateList.add(sdf.format(calBegin.getTime()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateList;
    }

    /**
     * 获取指定日期间的所有日期字符串
     *
     * @param dBeginStr
     * @param dEndStr
     * @param fromPattern
     * @param toPattern
     * @return
     */
    public static List<String> getBetweenDateStrList(String dBeginStr, String dEndStr, String fromPattern, String toPattern) {
        List<String> dateList = new ArrayList();

        try {
            dateList.add(dBeginStr);
            SimpleDateFormat fromFormat = new SimpleDateFormat(fromPattern);
            Date dBegin = fromFormat.parse(dBeginStr);
            Date dEnd = fromFormat.parse(dEndStr);
            SimpleDateFormat toFormat = new SimpleDateFormat(toPattern);

            Calendar calBegin = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calBegin.setTime(dBegin);
            Calendar calEnd = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calEnd.setTime(dEnd);
            // 测试此日期是否在指定日期之后
            while (dEnd.after(calBegin.getTime())) {
                // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
                calBegin.add(Calendar.DAY_OF_MONTH, 1);
                dateList.add(toFormat.format(calBegin.getTime()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateList;
    }

    public static List<String> getBetweenMonthStrList(String dBeginMonthStr, String dEndMonthStr, String pattern) {
        List<String> dateList = new ArrayList();

        try {
            dateList.add(dBeginMonthStr);
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date dBeginMonth = sdf.parse(dBeginMonthStr);
            Date dEndMonth = sdf.parse(dEndMonthStr);

            Calendar calBegin = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calBegin.setTime(dBeginMonth);
            Calendar calEnd = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calEnd.setTime(dEndMonth);
            // 测试此日期是否在指定日期之后
            while (dEndMonth.after(calBegin.getTime())) {
                // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
                calBegin.add(Calendar.MONTH, 1);
                dateList.add(sdf.format(calBegin.getTime()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateList;
    }

    /**
     * 对指定日期进行推移
     *
     * @param date
     * @param shift
     * @return
     */
    public static Date shiftDate(Date date, int shift) {
        if (date == null) {
            date = new Date();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (shift == 0) {
            return date;
        } else {
            calendar.add(Calendar.DATE, shift);
            return calendar.getTime();
        }
    }

    /**
     * 给时间加上几个小时
     *
     * @param day  当前时间 格式：yyyy-MM-dd HH:mm:ss
     * @param hour 需要加的时间
     * @return
     */
    public static String addDateMinut(String day, int hour) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(day);
        if (date == null) {
            return "";
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hour);// 24小时制
        date = cal.getTime();
        return format.format(date);
    }

    /**
     * 获取下个期间
     *
     * @param dateStr
     * @return
     */
    public static String getNextMonth(String dateStr, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date date = sdf.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, 1);
            return sdf.format(cal.getTime());
        } catch (ParseException e) {
            log.error(BasicException.stackTraceToString(e));
        }
        return null;
    }

    /**
     * 获取下个月月份，格式默认yyyy-MM
     *
     * @param pattern
     * @return
     */
    public static String getNextMonth(String pattern) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MONTH, 1);
        SimpleDateFormat format = null;
        if (StringUtils.isNotBlank(pattern)) {
            format = new SimpleDateFormat(pattern);
        } else {
            format = new SimpleDateFormat(MONTH_FORMAT);
        }
        return format.format(instance.getTime());
    }

    /**
     * 获取上个期间
     *
     * @param date
     * @return
     */
    public static String getLastMonth(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        return sdf.format(cal.getTime());
    }

    /**
     * 获取上个期间
     *
     * @param dateStr
     * @return
     */
    public static String getLastMonth(String dateStr, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date date = sdf.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, -1);
            return sdf.format(cal.getTime());
        } catch (ParseException e) {
            log.error(BasicException.stackTraceToString(e));
        }
        return null;
    }

    /**
     * 获取当前月，默认yyyy-MM格式
     *
     * @return
     */
    public static String getCurrentMonth() {
        return returnCurrentMonth("yyyy-MM");
    }

    /**
     * 获取当前月，默认yyyy-MM格式
     *
     * @param pattern
     * @return
     */
    public static String getCurrentMonth(String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = "yyyy-MM";
        }
        return returnCurrentMonth(pattern);
    }

    private static String returnCurrentMonth(String pattern) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
        String lastMonth = sdf.format(cal.getTime());
        return lastMonth;
    }

    /**
     * 取得上月第一天
     *
     * @return
     * @throws ParseException
     */
    public static Date getFirstDateOfLastMonth() throws ParseException {
        String lastMonth = DateUtils.getLastMonth(MONTH_FORMAT);
        SimpleDateFormat monthFormat = new SimpleDateFormat(MONTH_FORMAT);
        Date monthDate = monthFormat.parse(lastMonth);
        Calendar c = Calendar.getInstance();
        c.setTime(monthDate);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 取得上月最后一天
     *
     * @return
     * @throws ParseException
     */
    public static Date getLastDateOfLastMonth() throws ParseException {
        String lastMonth = DateUtils.getLastMonth(MONTH_FORMAT);
        SimpleDateFormat monthFormat = new SimpleDateFormat(MONTH_FORMAT);
        Date monthDate = monthFormat.parse(lastMonth);
        Calendar c = Calendar.getInstance();
        c.setTime(monthDate);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 获取上个月日期
     *
     * @return
     */
    public static Date getLastMonthDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    /**
     * 取得上月第一天
     *
     * @return
     * @throws ParseException
     */
    public static String getFirstDayOfLastMonth() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_FORMAT);
        return dateFormat.format(getFirstDateOfLastMonth());
    }

    /**
     * 取得上月最后一天
     *
     * @return
     * @throws ParseException
     */
    public static String getLastDayOfLastMonth() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_FORMAT);
        return dateFormat.format(getLastDateOfLastMonth());
    }

    /**
     * 是否为PDT时间
     * 美国太平洋时区的夏令时间始于每年三月的第二个星期六深夜二时整，并终于每年十一月的第一个星期日深夜二时整
     *
     * @param time
     * @return
     */
    public static boolean isPDT(long time) {
        Calendar dstStart = Calendar.getInstance();
        dstStart.set(Calendar.MONTH, 2); // 设为3月
        dstStart.set(Calendar.DAY_OF_MONTH, 1); // 设为第一天
        int satCount = 0;
        while (true) {
            if (dstStart.get(Calendar.MONTH) != 2) {
                break;
            }
            if (dstStart.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                satCount++;
            }
            if (satCount == 2) {
                break;
            }
            dstStart.add(Calendar.DATE, 1);
        }
        dstStart.set(Calendar.HOUR_OF_DAY, 2);
        dstStart.set(Calendar.MINUTE, 0);
        dstStart.set(Calendar.SECOND, 0);
        Date startDate = dstStart.getTime();

        Calendar dstEnd = Calendar.getInstance();
        dstEnd.set(Calendar.MONTH, 10); // 设为11月
        dstEnd.set(Calendar.DAY_OF_MONTH, 1); // 设为第一天
        int sunCount = 0;
        while (true) {
            if (dstEnd.get(Calendar.MONTH) == 11) {
                break;
            }
            if (dstEnd.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                sunCount++;
            }
            if (sunCount == 1) {
                break;
            }
            dstEnd.add(Calendar.DATE, 1);
        }
        dstEnd.set(Calendar.HOUR_OF_DAY, 2);
        dstEnd.set(Calendar.MINUTE, 0);
        dstEnd.set(Calendar.SECOND, 0);
        Date endDate = dstEnd.getTime();

        return time < endDate.getTime() && time > startDate.getTime();
    }

    /**
     * UTC时间转PDT时间
     *
     * @param utcPattern
     * @param utcTime
     * @param pdtPattern
     * @return
     * @throws ParseException
     */
    public static String utcTimeToPdtTime(String utcPattern, String utcTime, String pdtPattern) throws ParseException {
        SimpleDateFormat fromFormat = new SimpleDateFormat(utcPattern);
        fromFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat toFormat = new SimpleDateFormat(pdtPattern);
        toFormat.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));

        long time = fromFormat.parse(utcTime).getTime();
        return toFormat.format(new Date(time));
    }

    /**
     * PDT时间转UTC时间
     *
     * @param pdtPattern
     * @param pdtTime
     * @param utcPattern
     * @return
     * @throws ParseException
     */
    public static String pdtTimeToUtcTime(String pdtPattern, String pdtTime, String utcPattern) throws ParseException {
        SimpleDateFormat fromFormat = new SimpleDateFormat(pdtPattern);
        fromFormat.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));

        SimpleDateFormat toFormat = new SimpleDateFormat(utcPattern);
        toFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        long time = fromFormat.parse(pdtTime).getTime();
        return toFormat.format(new Date(time));
    }

    /**
     * 获取指定月份期间的所有月份字符串
     *
     * @param dBeginStr
     * @param dEndStr
     * @param parsePattern
     * @param formatPattern
     * @return
     */
    public static List<String> getBetweenMonthStrList(String dBeginStr, String dEndStr, String parsePattern, String formatPattern) {
        List<String> dateList = new ArrayList();

        try {
            SimpleDateFormat format1 = new SimpleDateFormat(parsePattern);
            SimpleDateFormat format2 = new SimpleDateFormat(formatPattern);
            Date dBegin = format1.parse(dBeginStr);
            Date dEnd = format1.parse(dEndStr);
            dateList.add(format2.format(dBegin));

            Calendar calBegin = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calBegin.setTime(dBegin);
            Calendar calEnd = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calEnd.setTime(dEnd);
            // 测试此日期是否在指定日期之后
            while (dEnd.after(calBegin.getTime())) {
                // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
                calBegin.add(Calendar.MONTH, 1);
                dateList.add(format2.format(calBegin.getTime()));
            }
        } catch (ParseException e) {
            log.error(BasicException.stackTraceToString(e));
        }

        return dateList;
    }

    /**
     * 获取月份内所有日期
     *
     * @param month
     * @param monthPattern
     * @return
     * @throws ParseException
     */
    public static List<String> getDateStrListByMonth(String month, String monthPattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(monthPattern);
        Date monthDate = format.parse(month);

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return getBetweenDateStrList(
                dateFormat.format(getFirstDateOfMonth(monthDate)),
                dateFormat.format(getLastDateOfMonth(monthDate)), DATE_FORMAT);
    }

    /**
     * 获取月份的倒数日期
     *
     * @param month
     * @param monthPattern
     * @param offset       倒数n天
     * @return
     */
    public static Date getLastOffsetDateByMonth(String month, String monthPattern, int offset) throws ParseException {
        SimpleDateFormat monthFormat = new SimpleDateFormat(monthPattern);
        Date monthDate = monthFormat.parse(month);
        Calendar cal = Calendar.getInstance();
        cal.setTime(monthDate);
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, offset * (-1));
        return cal.getTime();
    }

    public static Date getPenultDateOfMonth(String reportDateMonth, String monthPattern) throws ParseException {
        SimpleDateFormat monthFormat = new SimpleDateFormat(monthPattern);
        Date monthDate = monthFormat.parse(reportDateMonth);
        Date lastDateOfMonth = getLastDateOfMonth(monthDate);
        return addDays(lastDateOfMonth, -1);
    }

    /**
     * 判断是否为月的第一天
     *
     * @param date
     * @return
     */
    public static boolean isFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH) == 1;
    }

    /**
     * 获取昨天日期
     *
     * @return
     */
    public static String getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 获取昨天日期
     *
     * @return
     */
    public static Date getYesterdayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return setTime(calendar.getTime(), 0, 0, 0, 0);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    /**
     * 获取日期开始
     *
     * @param date
     * @return
     */
    public static Date getDateTimeStart(Date date) {
        return setTime(date, 0, 0, 0, 0);
    }

    /**
     * 获取日期结束
     *
     * @param date
     * @return
     */
    public static Date getDateTimeEnd(Date date) {
        return setTime(date, 23, 59, 59, 999);
    }

    public static Date getEasRateDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        // 倒数第一天
        if (day == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            return date;
        }
        // 倒数第二天
        if (day == (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - 1)) {
            return date;
        }

        if (day >= 1 && day <= 7) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        }
        if (day >= 8 && day <= 14) {
            calendar.set(Calendar.DAY_OF_MONTH, 8);
        }
        if (day >= 15 && day <= 21) {
            calendar.set(Calendar.DAY_OF_MONTH, 15);
        }
        if (day >= 22 && day <= (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - 2)) {
            calendar.set(Calendar.DAY_OF_MONTH, 22);
        }
        return calendar.getTime();
    }

    /**
     * 定义可用参数4种，例如：“20220101”，或者“20220101/20220115”，或者“20220101-20220131”，或者“0”
     * 单个日期执行，多个日期执行，区间日期执行，只识别yyyyMMdd格式；0表示执行默认任务
     *
     * @param jobDateParam
     * @return
     */
    public static List<Date> parseJobDateParam(String jobDateParam) {
        List<Date> jobDateList = new ArrayList<>();
        try {
            List<String> dateStrList;
            if (jobDateParam.contains("-")) {
                String[] dateSplit = jobDateParam.split("-");
                dateStrList = DateUtils.getBetweenDateStrList(dateSplit[0], dateSplit[1], "yyyyMMdd");
            } else {
                dateStrList = Arrays.asList(jobDateParam.split("/"));
            }
            for (String dateStr : dateStrList) {
                jobDateList.add(DateUtil.parse(dateStr, "yyyyMMdd"));
            }
        } catch (Exception e) {
            log.error(BasicException.stackTraceToString(e));
            throw new IllegalArgumentException("非法传参异常");
        }
        return jobDateList;
    }

    /**
     * 定义可用参数4种，例如：“202201”，或者“202201/202203”，或者“202201-202212”，或者“0”
     * 单个日期执行，多个日期执行，区间日期执行，只识别yyyyMM格式；0表示执行默认任务
     *
     * @param jobDateMonthParam
     * @return
     */
    public static List<Date> parseJobDateMonthParam(String jobDateMonthParam) {
        List<Date> jobDateList = new ArrayList<>();
        try {
            List<String> dateMonthStrList;
            if (jobDateMonthParam.contains("-")) {
                String[] dateSplit = jobDateMonthParam.split("-");
                dateMonthStrList = DateUtils.getBetweenMonthStrList(dateSplit[0], dateSplit[1], "yyyyMM");
            } else {
                dateMonthStrList = Arrays.asList(jobDateMonthParam.split("/"));
            }
            for (String dateMonthStr : dateMonthStrList) {
                jobDateList.add(DateUtil.parse(dateMonthStr, "yyyyMM"));
            }
        } catch (Exception e) {
            log.error(BasicException.stackTraceToString(e));
            throw new IllegalArgumentException("非法传参异常");
        }
        return jobDateList;
    }

    public static String getToday() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(new Date());
    }

    /**
     * 获取指定月份
     *
     * @param month
     * @param monthPattern
     * @param offset
     * @return
     * @throws ParseException
     */
    public static String getOffsetMonth(String month, String monthPattern, int offset) throws ParseException {
        SimpleDateFormat monthFormat = new SimpleDateFormat(monthPattern);
        Date monthDate = monthFormat.parse(month);
        Calendar cal = Calendar.getInstance();
        cal.setTime(monthDate);
        cal.add(Calendar.MONTH, offset);
        return monthFormat.format(cal.getTime());
    }

    public static String getEasRateDate(String dateString) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = df.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            // 倒数第一天
            if (day == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                return dateString;
            }
            // 倒数第二天
            if (day == (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - 1)) {
                return dateString;
            }

            if (day >= 1 && day <= 7) {
                calendar.set(Calendar.DAY_OF_MONTH, 1);
            }
            if (day >= 8 && day <= 14) {
                calendar.set(Calendar.DAY_OF_MONTH, 8);
            }
            if (day >= 15 && day <= 21) {
                calendar.set(Calendar.DAY_OF_MONTH, 15);
            }
            if (day >= 22 && day <= (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - 2)) {
                calendar.set(Calendar.DAY_OF_MONTH, 22);
            }
            date = calendar.getTime();
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> getMonthStrListByYear(int year, String pattern) {
        return DateUtils.getBetweenMonthStrList(year + "-01", year + "-12", pattern);
    }

}
