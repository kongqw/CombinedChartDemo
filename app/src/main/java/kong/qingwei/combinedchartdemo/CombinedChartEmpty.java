package kong.qingwei.combinedchartdemo;
import java.util.List;

/**
 * Created by kqw on 2016/5/13.
 * CombinedChartEmpty
 */
public class CombinedChartEmpty {
    private Object err_msg;
    private int err_no;
    private List<List<Long>> data;

    public void setErr_msg(Object err_msg) {
        this.err_msg = err_msg;
    }

    public void setErr_no(int err_no) {
        this.err_no = err_no;
    }

    public void setData(List<List<Long>> data) {
        this.data = data;
    }

    public Object getErr_msg() {
        return err_msg;
    }

    public int getErr_no() {
        return err_no;
    }

    public List<List<Long>> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "LKLineDataGson{" +
                "err_msg=" + err_msg +
                ", err_no=" + err_no +
                ", data=" + data +
                '}';
    }
}
