package kong.qingwei.combinedchartdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import kong.qingwei.combinedchartdemo.R;
import kong.qingwei.combinedchartdemo.bean.CombinedChartEntity;
import kong.qingwei.combinedchartdemo.net.CombinedChartConnector;
import kong.qingwei.combinedchartdemo.view.MyCombinedChart;

public class MainActivity extends AppCompatActivity {

    private MyCombinedChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 注册EventBus
        EventBus.getDefault().register(this);
        // 初始化控件
        mChart = (MyCombinedChart) findViewById(R.id.combinedChart);
        // 获取数据
        CombinedChartConnector.getInstance().connect();

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
}
