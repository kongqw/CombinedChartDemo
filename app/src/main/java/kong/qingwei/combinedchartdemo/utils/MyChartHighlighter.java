package kong.qingwei.combinedchartdemo.utils;

import com.github.mikephil.charting.highlight.ChartHighlighter;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;

/**
 * Created by kqw on 2016/5/16.
 * 自定义MyChartHighlighter
 */
public class MyChartHighlighter extends ChartHighlighter<BarLineScatterCandleBubbleDataProvider> {
    public MyChartHighlighter(BarLineScatterCandleBubbleDataProvider chart) {
        super(chart);
    }

    /**
     * 通过像素获取index
     *
     * @param x 像素
     * @return index
     */
    @Override
    public int getXIndex(float x) {
        return super.getXIndex(x);
    }
}
