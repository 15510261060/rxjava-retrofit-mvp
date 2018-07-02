package com.example.libcore.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.libcore.mvp.present.BasePresenter;
import com.example.libcore.mvp.view.IView;
import com.trello.rxlifecycle.components.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by fuchaoyang on 2018/6/17.<br/>
 * description：
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements IView{
  protected BaseActivity mActivity;

  public T presenter;
  private Unbinder unbinder;
  protected View contentView;
  private SweetAlertDialog sad;

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    mActivity = (BaseActivity) context;
  }


  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    presenter = bindPresent();
    contentView = initRootView(setContentViewBody());
    unbinder = ButterKnife.bind(this, contentView);
    initProgressDialog();
    return contentView;
  }

  private View initRootView(int contentViewBodyId) {
    if (contentViewBodyId > 0) {
      contentView = View.inflate(getHoldingActivity(), contentViewBodyId, null);
      LinearLayout activity_layout = new LinearLayout(getHoldingActivity());
      activity_layout.setOrientation(LinearLayout.VERTICAL);
      activity_layout.addView(contentView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
      return activity_layout;
    }
    return null;
  }

  protected BaseActivity getHoldingActivity() {
    return mActivity;
  }

  protected abstract T bindPresent();

  protected abstract int setContentViewBody();
  /**
   * 直接使用，不用转换View
   */
  @SuppressWarnings("unchecked") public static <T extends View> T findViewById(View view, int id) {
    return (T) view.findViewById(id);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    if (unbinder != null) {
      unbinder.unbind();
    }
  }

  private void initProgressDialog() {
    if (sad == null) {
      sad = new SweetAlertDialog(getHoldingActivity());
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

  @Override
  public void showLoading(boolean showLoading,boolean cancelable) {
    showProgressDialog("正在加载...", cancelable);
  }

  @Override
  public void dismissLoading() {
    dismissProgressDialog();
  }

}
