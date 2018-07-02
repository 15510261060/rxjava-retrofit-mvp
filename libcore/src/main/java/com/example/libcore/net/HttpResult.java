package com.example.libcore.net;

/**
 * Created by hebin
 */
public class HttpResult<T> {
    private static final int SUCCESS = 0;
    private int count;
    private int start;
    private int total;
    private int status;
    private String msg;
    T re;

    public boolean isSuccess() {
        return status == SUCCESS;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HttpResult{");
        sb.append("count=").append(count);
        sb.append(", start=").append(start);
        sb.append(", total=").append(total);
        sb.append(", status=").append(status);
        sb.append(", msg='").append(msg).append('\'');
        sb.append(", re=").append(re != null ? re.toString() : "");
        sb.append('}');
        return sb.toString();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getRe() {
        return re;
    }

    public void setRe(T re) {
        this.re = re;
    }

}
