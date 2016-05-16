package kong.qingwei.combinedchartdemo.utils;

import com.github.mikephil.charting.formatter.XAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Created by kqw on 2016/5/16.
 * 格式化横轴时间
 */
public class MyCustomXAxisValueFormatter implements XAxisValueFormatter {

    @Override
    public String getXValue(String original, int index, ViewPortHandler viewPortHandler) {
        long time = 0L;
        try {
            time = Long.parseLong(original);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return TimeUtil.dateFormat(time);
    }
}
