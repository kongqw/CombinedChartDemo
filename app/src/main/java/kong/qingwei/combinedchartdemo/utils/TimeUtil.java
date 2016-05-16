package kong.qingwei.combinedchartdemo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kqw on 2016/5/16.
 * TimeUtil
 */
public final class TimeUtil {

    /**
     * 格式化时间
     *
     * @param seconds 秒
     * @return yy/MM/dd
     */
    public static String dateFormat(long seconds) {
        try {
            Date date = new Date(seconds * 1000);
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            return format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
