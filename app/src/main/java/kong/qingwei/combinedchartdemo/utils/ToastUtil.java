package kong.qingwei.combinedchartdemo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by kqw on 2016/5/17.
 * Toast工具
 */
public final class ToastUtil {

    private static Toast mToast;

    // 工具类私有化
    private ToastUtil() {
    }

    // 单例模式 显示Toast
    public static void show(Context context, String text) {
        if (null == mToast) {
            mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        try {
            mToast.setText(text);
            mToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 关闭Toast
    public static void cancel() {
        if (null != mToast)
            mToast.cancel();
    }
}
