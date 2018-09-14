package com.wesley.growth.log;

import org.apache.commons.lang3.time.FastDateFormat;

/**
 * <p>
 *
 * </p>
 *
 * @author yani
 * Email yani@uoko.com
 * Created by 2018/09/11
 */
public class DateUtils {


    private DateUtils(){}

    private static DateUtils instance;

    public static DateUtils getInstance(){
        if (instance == null) {
            instance = new DateUtils();
        }

        return instance;
    }

    FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    public long getTime(String time) throws Exception {
        return format.parse(time.substring(1, time.length()-1)).getTime();
    }

}
