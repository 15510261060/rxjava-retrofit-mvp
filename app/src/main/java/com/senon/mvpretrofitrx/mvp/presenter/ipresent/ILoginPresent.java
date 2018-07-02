package com.senon.mvpretrofitrx.mvp.presenter.ipresent;

import java.util.HashMap;

public interface ILoginPresent {
    //请求1
    void login(HashMap<String, String> map, boolean isDialog, boolean cancelable);

    //请求2
    void logout(HashMap<String, String> map, boolean isDialog, boolean cancelable);
}
