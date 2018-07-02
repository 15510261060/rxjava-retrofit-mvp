package com.example.libcore.base;


import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import com.example.libcore.mvp.present.BasePresenter;
import com.example.libcore.mvp.present.IBZView;
import com.example.libcore.mvp.view.IView;
import com.trello.rxlifecycle.components.support.RxFragmentActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by fuchaoyang on 2018/6/17.<br/>
 * description：
 */

public abstract class BaseActivity<P extends BasePresenter> extends RxFragmentActivity implements IView{
  protected Activity mContext;

  private SweetAlertDialog sad;
  public P present;
  private Unbinder unbinder;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mContext = this;
    setContentView(setContentViewBody());
    unbinder = ButterKnife.bind(this);
    initView();

    present = bindPresent();
    if (present != null) {
      present.initialize();
    }
    initProgressDialog();
  }

  protected abstract P bindPresent();

  /**
   * 执行在setContentView()及Delegate初始化之后，getAdapter()之前
   */
  protected abstract void initView();

  protected abstract int setContentViewBody();


  private void initProgressDialog() {
    if (sad == null) {
      sad = new SweetAlertDialog(this);
      sad.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);

    }
  }

  private void showProgressDialog(String title, boolean cancelable){
    if (!sad.isShowing()) {
      sad.setTitleText(title);
      sad.setCancelable(cancelable);
      sad.show();
    }
  }

  private void dismissProgressDialog() {
    if (sad != null) {
      sad.dismiss();
    }
  }


  @Override protected void onDestroy() {
    if (unbinder != null) { unbinder.unbind(); }
    super.onDestroy();
  }

  @Override
  public void showLoading(boolean showLoading,boolean cancelable) {
    showProgressDialog("正在加载...", cancelable);
  }

  @Override
  public void dismissLoading() {
    dismissProgressDialog();
  }
}
