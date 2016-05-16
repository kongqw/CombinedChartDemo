package kong.qingwei.combinedchartdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import kong.qingwei.combinedchartdemo.R;
import kong.qingwei.combinedchartdemo.bean.CombinedChartEntity;
import kong.qingwei.combinedchartdemo.listener.OnValueSelectedListener;
import kong.qingwei.combinedchartdemo.net.CombinedChartConnector;
import kong.qingwei.combinedchartdemo.view.MyCombinedChart;

public class MainActivity extends AppCompatActivity implements OnValueSelectedListener {

    private MyCombinedChart mChart;
    private LinearLayout mDetails;
    private TextView mOpen;
    private TextView mClose;
    private TextView mHigh;
    private TextView mLow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 注册EventBus
        EventBus.getDefault().register(this);
        // 初始化控件
        mChart = (MyCombinedChart) findViewById(R.id.combinedChart);
        mChart.setOnValueSelectedListener(this);
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
        mChart.setData(empty);
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
