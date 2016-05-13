package kong.qingwei.combinedchartdemo;

import android.graphics.Color;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CombinedChart mChart;
    private Vibrator mVibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 注册EventBus
        EventBus.getDefault().register(this);

        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        CombinedChartConnector.getInstance().connect();


        mChart = (CombinedChart) findViewById(R.id.chart1);
        mChart.setDescription("");
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);

        // draw bars behind lines
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.BUBBLE, CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.SCATTER
        });

        // 取消Y轴缩放动画
        mChart.setScaleYEnabled(false);

        mChart.setAutoScaleMinMaxEnabled(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
//        leftAxis.setStartAtZero(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);

        mChart.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                mVibrator.vibrate(100);
                Toast.makeText(getApplicationContext(), "长按", Toast.LENGTH_SHORT).show();
                mChart.setDragEnabled(false);
                mChart.getData().setHighlightEnabled(true);
                return false;
            }
        });

        mChart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        mChart.setDragEnabled(true);
                        mChart.getData().setHighlightEnabled(false);
//                        Toast.makeText(getApplicationContext(), "抬起", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

//        mChart.setOnTouchListener(new ChartTouchListener(mChart) {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()){
//                    case MotionEvent.ACTION_CANCEL:
//                    case MotionEvent.ACTION_UP:
//                        Toast.makeText(getApplicationContext(),"抬起",Toast.LENGTH_SHORT).show();
//                        break;
//                }
//                return true;
//            }
//        });

//        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//            @Override
//            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
//                Log.i("onValueSelected", "dataSetIndex = " + dataSetIndex);
//            }
//
//            @Override
//            public void onNothingSelected() {
//
//            }
//        });

    }

    @Override
    protected void onDestroy() {
        // 反注册EventBus
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 获取K线数据
     *
     * @param empty K线数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CombinedChartEmpty empty) {

        ArrayList<String> mY = new ArrayList();

        for (int i = 0; i < empty.getData().size(); i++) {
            mY.add(empty.getData().get(i).get(5) + "");
        }

        CombinedData data = new CombinedData(mY);
        data.setData(generateCandleData(empty));
        data.setData(generateLineData(empty));
//        data.setData(generateBarData(empty));
//        data.setData(generateBubbleData());
//         data.setData(generateScatterData());
        mChart.setData(data);

        mChart.notifyDataSetChanged();
        // 最多显示60组数据
        mChart.setVisibleXRangeMaximum(60);
        // 最少显示30组数据
        mChart.setVisibleXRangeMinimum(30);
        // 移动到最右侧数据
        mChart.moveViewToX(empty.getData().size() - 1);

        mChart.invalidate();
    }

    protected CandleData generateCandleData(CombinedChartEmpty empty) {
        CandleData d = new CandleData();
        ArrayList<CandleEntry> entries = new ArrayList<>();
        for (int index = 0; index < empty.getData().size(); index++) {
            long a = empty.getData().get(index).get(1) / 1000;
            long b = empty.getData().get(index).get(2) / 1000;
            long c = empty.getData().get(index).get(3) / 1000;
            long dd = empty.getData().get(index).get(4) / 1000;
            entries.add(new CandleEntry(index, a, b, c, dd));
        }
        CandleDataSet set = new CandleDataSet(entries, "Candle DataSet");
        set.setColor(Color.rgb(80, 80, 80));
//        set.setBodySpace(0.3f);
        set.setValueTextSize(10f);
        set.setDrawValues(false);
        d.addDataSet(set);
        return d;
    }


    private LineData generateLineData(CombinedChartEmpty empty) {
        LineData d = new LineData();
        d.addDataSet(getLineDataSet(5, empty));
        d.addDataSet(getLineDataSet(10, empty));
        d.addDataSet(getLineDataSet(30, empty));
        return d;
    }

    private LineDataSet getLineDataSet(int ma, CombinedChartEmpty empty) {

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


    private BarData generateBarData(CombinedChartEmpty empty) {

        BarData d = new BarData();

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int index = 0; index < empty.getData().size(); index++) {
            long c = empty.getData().get(index).get(3) / 1000;
            entries.add(new BarEntry(c, index));

        }

        BarDataSet set = new BarDataSet(entries, "Bar DataSet");
        set.setColor(Color.rgb(60, 220, 78));
        set.setValueTextColor(Color.rgb(60, 220, 78));
        set.setValueTextSize(10f);
        d.addDataSet(set);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        return d;
    }

}
