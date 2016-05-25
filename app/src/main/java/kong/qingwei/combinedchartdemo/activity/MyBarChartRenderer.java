package kong.qingwei.combinedchartdemo.activity;

import android.graphics.Canvas;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Created by kongqw on 2016/5/25.
 */
public class MyBarChartRenderer extends BarChartRenderer {
    public MyBarChartRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    @Override
    public void drawHighlighted(Canvas c, Highlight[] indices) {
//        super.drawHighlighted(c, indices);
    }
}
