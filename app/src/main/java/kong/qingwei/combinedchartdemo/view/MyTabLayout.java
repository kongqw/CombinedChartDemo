package kong.qingwei.combinedchartdemo.view;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import kong.qingwei.combinedchartdemo.R;
import kong.qingwei.combinedchartdemo.listener.OnPopupWindowItemClickListener;


/**
 * Created by kqw on 2016/5/17.
 * 模拟自选股导航栏
 */
public class MyTabLayout extends TabLayout implements View.OnClickListener, OnPopupWindowItemClickListener, TabLayout.OnTabSelectedListener {

    private final String TAG = this.getClass().getSimpleName();

    private String[] mTitles = new String[]{
            "分时",
            "日K",
            "周K",
            "月K"};
    private MyPopupWindow myPopupWindow;
    private Button mButton;

    public MyTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initMyTabLayout(context);
    }

    private void initMyTabLayout(Context context) {
        for (int i = 0; i < 5; i++) {
            Tab tab = newTab();
            tab.setCustomView(getTabView(i));
            addTab(tab);
        }
        setOnTabSelectedListener(this);
        // PopupWindow
        myPopupWindow = new MyPopupWindow(context);
        myPopupWindow.setOnPopupWindowItemClickListener(this);
    }

    public View getTabView(int position) {
        Log.i(this.TAG, "getTabView( " + position + " )");
        View v = null;
        switch (position) {
            case 0:
            case 1:
            case 2:
            case 3:
                v = LayoutInflater.from(getContext().getApplicationContext()).inflate(R.layout.custom_tab, null);
                final TextView tv = (TextView) v.findViewById(R.id.textView);
                tv.setText(mTitles[position]);

                break;
            case 4:
                v = LayoutInflater.from(getContext().getApplicationContext()).inflate(R.layout.custom_tab2, null);
                mButton = (Button) v.findViewById(R.id.button);
                mButton.setOnClickListener(this);
                break;
        }
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                myPopupWindow.toggle(v);
                break;
        }
    }

    @Override
    public void click(int position) {
        mButton.setText(myPopupWindow.getContent(position));
        getTabAt(getTabCount() - 1).select();
    }

    @Override
    public void onTabSelected(Tab tab) {

    }

    @Override
    public void onTabUnselected(Tab tab) {
        if (getTabCount() - 1 == tab.getPosition()) {
            mButton.setText("分钟");
        }
    }

    @Override
    public void onTabReselected(Tab tab) {

    }
}
