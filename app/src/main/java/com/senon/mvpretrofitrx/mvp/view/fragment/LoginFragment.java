package com.senon.mvpretrofitrx.mvp.view.fragment;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.libcore.base.BaseFragment;
import com.example.libcore.utils.LogUtil;
import com.senon.mvpretrofitrx.R;
import com.senon.mvpretrofitrx.mvp.entity.Login;
import com.senon.mvpretrofitrx.mvp.presenter.LoginPresenter;
import com.senon.mvpretrofitrx.mvp.view.ApiAction;
import com.senon.mvpretrofitrx.mvp.view.iview.ILoginView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：hebin
 *
 */

public class LoginFragment extends BaseFragment<LoginPresenter> implements ILoginView {

    @BindView(R.id.fragment_msg_tv)
    TextView fragment_msg_tv;
    @BindView(R.id.fragment_check_btn)
    Button fragment_check_btn;


    @OnClick({R.id.fragment_msg_tv, R.id.fragment_check_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_msg_tv:
                break;
            case R.id.fragment_check_btn:
                fragment_msg_tv.setText("");
                HashMap<String,String> map = new HashMap<>();
                map.put("type","yuantong");
                map.put("postid","11111111111");
                presenter.login(map,true, true);
                break;
        }
    }

    @Override
    protected LoginPresenter bindPresent() {
        return new LoginPresenter(getHoldingActivity(),this);
    }

    @Override
    protected int setContentViewBody() {
        return R.layout.fragment_layout;
    }

    @Override
    public void onSuccess(ApiAction action, Object o) {
        if (action.equals(ApiAction.LOGIN)){
            List<Login> logins = (List<Login>) o;
            LogUtil.e(logins.toString());
            fragment_msg_tv.setText(logins.toString());
        }else if (action.equals(ApiAction.LOGOUT)){
            List<Login> logins = (List<Login>) o;
            LogUtil.e(logins.toString());
            fragment_msg_tv.setText(logins.toString());
        }
    }

    @Override
    public void onError(ApiAction action, int code, String message) {

    }

    @Override
    public void onStartUp(ApiAction action) {

    }

    @Override
    public void onCompleted(ApiAction action) {

    }
}
