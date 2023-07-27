package util.login.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import org.apache.commons.collections4.CollectionUtils;
import org.example.util.exception.BusinessException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kone
 * @date 2022/8/29
 */
public class DateUtil {
    public static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String timestampToStr(Long timestamp) {
        if (null == timestamp) {
            return null;
        }

        return timestampToStr(timestamp, YYYY_MM_DD_HH_MM);
    }

    public static String timestampToStr(Long timestamp, String format) {
        if (null == timestamp) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(timestamp * 1000);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);

        return dateTimeFormatter.format(dateTime);
    }

    public static List<String> getBetweenDays(String startDate,String endDate){

        Date startTime = cn.hutool.core.date.DateUtil.parse(startDate, "yyyy-MM-dd");
        Date endTime = cn.hutool.core.date.DateUtil.parse(endDate, "yyyy-MM-dd");

        List<DateTime> dateTimes = cn.hutool.core.date.DateUtil.rangeToList(startTime, endTime, DateField.DAY_OF_YEAR);
        if (CollectionUtils.isEmpty(dateTimes)) {
            throw new BusinessException(500,"日期不合法");
        }
        List<String> dateList = dateTimes.stream().map(item -> cn.hutool.core.date.DateUtil.format(item, "yyyy-MM-dd")).collect(Collectors.toList());
        return dateList;
    }

    public static String formatTimeMinute(Long time){
        String timeStr="";
        if (time==null){
            return null;
        }
        //时
        Long hour = time / 60 / 60;
        //分
        Long minutes = time / 60 % 60;
        //秒
        Long remainingSeconds = time % 60;
        //判断时分秒是否小于10……
        if (hour < 10){
            timeStr = hour + "时" + minutes + "分" + remainingSeconds+"秒";
        }else if (minutes < 10){
            timeStr = hour + "时" + minutes + "分" + remainingSeconds+"秒";
        }else if (remainingSeconds < 10){
            timeStr = hour + "时" + minutes + "分" + "0" + remainingSeconds+"秒";
        }else {
            timeStr = hour + "时" + minutes + "分" + remainingSeconds+"秒";
        }
        return timeStr;
    }
}
