package com.example.libcore.mvp.view;

public interface IView {

    void showLoading(boolean showLoading, boolean cancelable);

    void dismissLoading();

}
