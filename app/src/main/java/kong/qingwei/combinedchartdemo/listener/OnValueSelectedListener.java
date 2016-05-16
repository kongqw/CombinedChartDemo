package kong.qingwei.combinedchartdemo.listener;

/**
 * Created by kqw on 2016/5/16.
 * OnValueSelectedListener
 */
public interface OnValueSelectedListener {
    public void start();

    public void data(long open, long close, long high, long low);

    public void end();
}
