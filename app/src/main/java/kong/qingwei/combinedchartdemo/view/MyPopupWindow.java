package kong.qingwei.combinedchartdemo.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.PopupWindow;

import kong.qingwei.combinedchartdemo.R;
import kong.qingwei.combinedchartdemo.adapter.MyPopupWindowAdapter;
import kong.qingwei.combinedchartdemo.listener.OnPopupWindowItemClickListener;

/**
 * Created by kqw on 2016/5/17.
 * MyPopupWindow
 */
public class MyPopupWindow extends PopupWindow implements View.OnKeyListener, OnPopupWindowItemClickListener {

    private PopupWindow mPopupWindow;
    private final View mContentView;
    private OnPopupWindowItemClickListener mOnPopupWindowItemClickListener;
    private MyPopupWindowAdapter mPopupWindowAdapter;

    public MyPopupWindow(Context context) {
        // 获取弹出的PopupWindow的界面
        mContentView = View.inflate(context, R.layout.popupwindow, null);
        initRecyclerView(context);
        // 监听点击返回键
        mContentView.setOnKeyListener(this);
        // 创建一个PopupWindow并默认获取焦点（如果没有焦点view无法监听到点击事件）
        mPopupWindow = new PopupWindow(mContentView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        // ***给PopupWindow设置背景
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow之外的其他位置消失
        mPopupWindow.setOutsideTouchable(true);

        mPopupWindow.update();
    }

    private void initRecyclerView(Context context) {
        RecyclerView mRecyclerView = (RecyclerView) mContentView.findViewById(R.id.recyclerView);
        // 如果数据的填充不会改变RecyclerView的布局大小，那么这个设置可以提高RecyclerView的性能
        mRecyclerView.setHasFixedSize(true);
        // 设置这个RecyclerView是线性布局
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 给RecyclerView添加一个适配器显示数据
        mPopupWindowAdapter = new MyPopupWindowAdapter();
        // 添加PopupWindow条目点击的监听
        mPopupWindowAdapter.setOnPopupWindowItemClickListener(this);
        mRecyclerView.setAdapter(mPopupWindowAdapter);
    }


    /**
     * 设置动画
     */
    public void startAnimation() {
        // 要想动画能够正常播放 必须给PopupWindow设置背景
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置动画从没有弹出到本身大小
        ScaleAnimation animation = new ScaleAnimation(1, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0f);
        // 动画时间
        animation.setDuration(200);
        // 动画弹出
        mContentView.startAnimation(animation);
    }

    /**
     * 显示PopupWindow
     *
     * @param parent 父控件
     */
    public void showPopupWindow(View parent) {
        int[] location = new int[2];
        // 测量被点击的view在窗体中的位置  将位置保存在一个int数组中  便于设置PopupWindow显示的位置
        parent.getLocationInWindow(location);
        // 将PopupWindow显示在指定位置上
        // mPopupWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.LEFT, location[0], location[1]);
        mPopupWindow.showAtLocation(parent, Gravity.TOP | Gravity.START, location[0], location[1] + parent.getHeight());
    }

    /**
     * 关闭PopupWindow
     */
    public void dismiss() {
        if (null != mPopupWindow && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * 开关
     */
    public void toggle(View parent) {
        if (mPopupWindow.isShowing()) {
            dismiss();
        } else {
            showPopupWindow(parent);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                // 按返回键关闭PopupWindow
                dismiss();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void click(int position) {
        mOnPopupWindowItemClickListener.click(position);
        dismiss();
        Log.i("MyPopupWindow","position = " + position);
    }

    /**
     * 添加条目点击的监听
     *
     * @param listener 回调接口
     */
    public void setOnPopupWindowItemClickListener(OnPopupWindowItemClickListener listener) {
        mOnPopupWindowItemClickListener = listener;
    }

    public String getContent(int position) {
        return mPopupWindowAdapter.getContent(position);
    }
}
