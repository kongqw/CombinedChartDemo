package kong.qingwei.combinedchartdemo.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

import kong.qingwei.combinedchartdemo.bean.CombinedChartEntity;
import kong.qingwei.combinedchartdemo.utils.MyCustomXAxisValueFormatter;

/**
 * Created by kqw on 2016/5/25.
 * 柱形图
 */
public class MyBarChart extends BarChart {
    public MyBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initChart();


    }

    private void initChart() {
        setDescription("");
        setBackgroundColor(Color.WHITE);
        setDrawGridBackground(false);
        setDrawBarShadow(false);

        // 取消Y轴缩放动画
        setScaleYEnabled(false);

        // 自动缩放调整
        setAutoScaleMinMaxEnabled(true);

        /*
        * Y轴
        * ******************************************************************************/
        YAxis left = getAxisLeft();
        // 左侧Y轴坐标
        left.setDrawLabels(true);
        // 左侧Y轴
        left.setDrawAxisLine(true);
        // 横向线
        left.setDrawGridLines(true);
        // 纵轴数据显示在图形内部
        left.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        // 不显示右侧Y轴
        getAxisRight().setEnabled(false);

        /*
        * X轴
        * ******************************************************************************/
        XAxis xAxis = getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 格式化X轴时间
        xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());


        setDrawBarShadow(false);
        setDrawValueAboveBar(true);

        setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        setPinchZoom(false);

        setDrawGridBackground(false);

    }


    /**
     * 填充数据
     *
     * @param entity 数据实体
     */
    public void setData(CombinedChartEntity entity) {
        int count = entity.getData().size();
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add(entity.getData().get(i).get(0) + "");
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < count; i++) {
            float val = (float) (entity.getData().get(i).get(5));
            yVals1.add(new BarEntry(val, i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "DataSet");
        set1.setBarSpacePercent(35f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
//        data.setValueTypeface(mTf);

        setData(data);

        // 最多显示60组数据
        setVisibleXRangeMaximum(60);
        // 最少显示30组数据
        setVisibleXRangeMinimum(30);
        // 移动到最右侧数据
        moveViewToX(entity.getData().size() - 1);
    }

}
