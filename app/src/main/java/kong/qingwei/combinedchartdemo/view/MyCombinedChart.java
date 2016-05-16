package kong.qingwei.combinedchartdemo.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import kong.qingwei.combinedchartdemo.bean.CombinedChartEntity;

/**
 * Created by kqw on 2016/5/16.
 * MyCombinedChart
 */
public class MyCombinedChart extends CombinedChart {
    public MyCombinedChart(Context context) {
        super(context);
        initChart();
    }

    public MyCombinedChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initChart();
    }

    public MyCombinedChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initChart();
    }

    private void initChart() {
        setDescription("");
        setBackgroundColor(Color.WHITE);
        setDrawGridBackground(false);
        setDrawBarShadow(false);

        // draw bars behind lines
        setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.BUBBLE, CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.SCATTER
        });

        // 取消Y轴缩放动画
        setScaleYEnabled(false);

        setAutoScaleMinMaxEnabled(true);

        YAxis rightAxis = getAxisRight();
        rightAxis.setDrawGridLines(false);

        YAxis leftAxis = getAxisLeft();
        leftAxis.setDrawGridLines(false);
//        leftAxis.setStartAtZero(false);

        XAxis xAxis = getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
//        xAxis.setYOffset(80);


        setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                mVibrator.vibrate(100);
                Toast.makeText(getContext().getApplicationContext(), "长按", Toast.LENGTH_SHORT).show();
                setDragEnabled(false);
                getData().setHighlightEnabled(true);
                return false;
            }
        });

        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        setDragEnabled(true);
                        getData().setHighlightEnabled(false);
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 填充数据
     *
     * @param entity 数据实体
     */
    public void setData(CombinedChartEntity entity) {
        ArrayList<String> mY = new ArrayList();
        for (int i = 0; i < entity.getData().size(); i++) {
            mY.add(entity.getData().get(i).get(5) + "");
        }
        CombinedData data = new CombinedData(mY);
        data.setData(generateCandleData(entity));
        data.setData(generateLineData(entity));
        //        data.setData(generateBarData(empty));
        //        data.setData(generateBubbleData());
        //        data.setData(generateScatterData());
        setData(data);

        notifyDataSetChanged();
        // 最多显示60组数据
        setVisibleXRangeMaximum(60);
        // 最少显示30组数据
        setVisibleXRangeMinimum(30);
        // 移动到最右侧数据
        moveViewToX(entity.getData().size() - 1);
        // 显示
        invalidate();
    }

    protected CandleData generateCandleData(CombinedChartEntity entity) {
        CandleData d = new CandleData();
        ArrayList<CandleEntry> entries = new ArrayList<>();
        for (int index = 0; index < entity.getData().size(); index++) {
            long a = entity.getData().get(index).get(1) / 1000;
            long b = entity.getData().get(index).get(2) / 1000;
            long c = entity.getData().get(index).get(3) / 1000;
            long dd = entity.getData().get(index).get(4) / 1000;
            entries.add(new CandleEntry(index, a, b, c, dd));
        }
        CandleDataSet set = new CandleDataSet(entries, "Candle DataSet");
        set.setColor(Color.rgb(80, 80, 80));
        set.setValueTextSize(10f);
        set.setDrawValues(false);
        d.addDataSet(set);
        return d;
    }

    private LineData generateLineData(CombinedChartEntity entity) {
        LineData d = new LineData();
        d.addDataSet(getLineDataSet(5, entity));
        d.addDataSet(getLineDataSet(10, entity));
        d.addDataSet(getLineDataSet(30, entity));
        return d;
    }

    private LineDataSet getLineDataSet(int ma, CombinedChartEntity empty) {

        ArrayList<Entry> entries = new ArrayList<>();
        for (int index = ma - 1; index < empty.getData().size(); index++) {
            long sum = 0;
            for (int m = 0; m < ma; m++) {
                sum += (empty.getData().get(index - m).get(3) / 1000);
            }
            sum /= ma;
            entries.add(new Entry(sum, index));
        }
        LineDataSet set = new LineDataSet(entries, "MA " + ma);
        set.setColor(5 == ma ? Color.rgb(240, 0, 70) : 10 == ma ? Color.rgb(0, 0, 70) : Color.rgb(100, 100, 255));
        set.setLineWidth(1f);
        set.setDrawCircles(false);
        set.setDrawCubic(false);
        set.setDrawValues(false);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        return set;
    }
}
