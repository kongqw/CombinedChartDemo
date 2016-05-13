package kong.qingwei.combinedchartdemo;

import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

/**
 * Created by kongqw on 2015/11/28.
 * 获取K线数据的类
 */
public class CombinedChartConnector {

    private static CombinedChartConnector mConnector;
    private static OkHttpClient mOkHttpClient;
    private static Call mCall;
    private static Gson mGson;

    private CombinedChartConnector() {

    }

    public static CombinedChartConnector getInstance() {
        if (null == mConnector || null == mGson || null == mOkHttpClient) {
            mConnector = new CombinedChartConnector();
            mGson = new Gson();
            //创建okHttpClient对象
            mOkHttpClient = new OkHttpClient();
        }
        return mConnector;
    }

    /**
     * 发起请求
     */
    public void connect() {
        //创建一个Request
        Request request = new Request.Builder()
//                .url("https://price.api.btc.com/v1/ticker/line?symbol=okcoinbtccny&interval=1d&offset=0&limit=100")
                .url("https://price.api.btc.com/dev/ticker/line?symbol=huobibtccny&interval=1d&offset=-600&limit=600")
                .build();
        //new call
        mCall = mOkHttpClient.newCall(request);
        //请求加入调度
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String htmlStr = response.body().string();
                Log.i("CombinedChartConnector","htmlStr = " + htmlStr);
                CombinedChartEmpty empty = mGson.fromJson(htmlStr,CombinedChartEmpty.class);
                EventBus.getDefault().post(empty);
            }
        });
    }
}
