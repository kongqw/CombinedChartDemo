package kong.qingwei.combinedchartdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import kong.qingwei.combinedchartdemo.R;
import kong.qingwei.combinedchartdemo.bean.CombinedChartEntity;
import kong.qingwei.combinedchartdemo.listener.CoupleChartGestureListener;
import kong.qingwei.combinedchartdemo.listener.OnValueSelectedListener;
import kong.qingwei.combinedchartdemo.net.CombinedChartConnector;
import kong.qingwei.combinedchartdemo.view.MyBarChart;
import kong.qingwei.combinedchartdemo.view.MyCombinedChart;

public class MainActivity extends AppCompatActivity implements OnValueSelectedListener {

    private MyCombinedChart mCombinedChart;
    private LinearLayout mDetails;
    private TextView mOpen;
    private TextView mClose;
    private TextView mHigh;
    private TextView mLow;
    private MyBarChart mBarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 注册EventBus
        EventBus.getDefault().register(this);
        // 初始化控件
        mCombinedChart = (MyCombinedChart) findViewById(R.id.combinedChart);
        mBarChart = (MyBarChart) findViewById(R.id.barChart);

        // 将K线控的滑动事件传递给交易量控件
        mCombinedChart.setOnChartGestureListener(new CoupleChartGestureListener(mCombinedChart, new Chart[]{mBarChart}));
        // 将交易量控件的滑动事件传递给K线控件
        mBarChart.setOnChartGestureListener(new CoupleChartGestureListener(mBarChart, new Chart[]{mCombinedChart}));


        mCombinedChart.setOnValueSelectedListener(this);
        // 获取数据
        CombinedChartConnector.getInstance().connect();


        mDetails = (LinearLayout) findViewById(R.id.details);
        mOpen = (TextView) findViewById(R.id.open);
        mClose = (TextView) findViewById(R.id.close);
        mHigh = (TextView) findViewById(R.id.high);
        mLow = (TextView) findViewById(R.id.low);

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
    public void onEvent(CombinedChartEntity empty) {
        // 填充数据
        mCombinedChart.setData(empty);
        mBarChart.setData(empty);
    }

    @Override
    public void start() {
        mDetails.setVisibility(View.VISIBLE);
    }

    @Override
    public void data(long open, long close, long high, long low) {
//        Log.i("data", "open = " + open + "  close = " + close + "  high = " + high + "  low = " + low);
        mOpen.setText(String.valueOf(open));
        mClose.setText(String.valueOf(close));
        mHigh.setText(String.valueOf(high));
        mLow.setText(String.valueOf(low));
    }

    @Override
    public void end() {
        mDetails.setVisibility(View.GONE);
    }
}
