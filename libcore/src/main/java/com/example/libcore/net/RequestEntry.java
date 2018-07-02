package com.example.libcore.net;

import java.util.HashMap;
import java.util.Map;

public class RequestEntry {
    public boolean isGet = false;
    public String url = "";
    public RequestCallBack callBack = null;
    public String domain = "";
    public int timeout = 45 * 1000;
    public boolean isImg = false;
    public Map<String,Object> params=new HashMap<String,Object>();
    public boolean cacheType;
}