package kong.qingwei.combinedchartdemo.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kong.qingwei.combinedchartdemo.R;
import kong.qingwei.combinedchartdemo.listener.OnPopupWindowItemClickListener;

/**
 * Created by kqw on 2016/5/17.
 * PopupWindow的数据适配器
 */
public class MyPopupWindowAdapter extends RecyclerView.Adapter<MyPopupWindowAdapter.ViewHolder> {

    private OnPopupWindowItemClickListener mOnPopupWindowItemClickListener;
    private String[] mItems = {"5分钟", "15分钟", "30分钟", "60分钟"};

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
            mTextView = (TextView) v.findViewById(R.id.text_item);
        }
    }

    public MyPopupWindowAdapter() {
    }

    @Override
    public MyPopupWindowAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popup_window, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTextView.setText(mItems[position]);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnPopupWindowItemClickListener)
                    mOnPopupWindowItemClickListener.click(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == mItems ? 0 : mItems.length;
    }

    /**
     * 添加条目点击的监听
     *
     * @param listener 回调接口
     */
    public void setOnPopupWindowItemClickListener(OnPopupWindowItemClickListener listener) {
        mOnPopupWindowItemClickListener = listener;
    }

    /**
     * 获取指定位置的Item内容
     *
     * @param position 位置
     * @return 内容
     */
    public String getContent(int position) {
        return mItems[position];
    }
}
