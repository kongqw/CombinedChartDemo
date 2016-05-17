package kong.qingwei.combinedchartdemo.view;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import kong.qingwei.combinedchartdemo.R;


/**
 * Created by kqw on 2016/5/17.
 * 模拟自选股导航栏
 */
public class MyTabLayout extends TabLayout {

    private final String TAG = this.getClass().getSimpleName();

    private String[] mTitles = new String[]{
            "分时",
            "日K",
            "周K",
            "月K"};
    private Spinner spinner;

    public MyTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initMyTabLayout();
    }

    private void initMyTabLayout() {
        for (int i = 0; i < 5; i++) {
            Tab tab = newTab();
            tab.setCustomView(getTabView(i));
            addTab(tab);
        }
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
//                spinner = (Spinner) v.findViewById(R.id.spinner);
//                // 建立数据源
//                String[] mItems = {"5分钟", "15分钟", "30分钟", "60分钟"};
//                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext().getApplicationContext(), android.R.layout.simple_spinner_item, mItems);
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinner.setAdapter(adapter);
//                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                        // TODO
//                        getTabAt(4).select();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//                        // TODO
//                    }
//                });
                break;
        }
        return v;
    }

}
