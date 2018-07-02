package com.senon.mvpretrofitrx.mvp.view.activity;


import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.libcore.base.BaseActivity;
import com.example.libcore.utils.LogUtil;
import com.example.libcore.utils.ToastUtil;
import com.senon.mvpretrofitrx.R;
import com.senon.mvpretrofitrx.mvp.entity.Login;
import com.senon.mvpretrofitrx.mvp.presenter.LoginPresenter;
import com.senon.mvpretrofitrx.mvp.view.ApiAction;
import com.senon.mvpretrofitrx.mvp.view.fragment.LoginFragment;
import com.senon.mvpretrofitrx.mvp.view.iview.ILoginView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView {

    @BindView(R.id.main_msg_tv)
    TextView main_msg_tv;
    @BindView(R.id.main_check_btn)
    Button main_check_btn;
    @BindView(R.id.main_check2_btn)
    Button main_check2_btn;
    @BindView(R.id.frame_lay)
    FrameLayout frame_lay;

    @OnClick({R.id.main_msg_tv, R.id.main_check_btn,R.id.main_check2_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_msg_tv:
                break;
            case R.id.main_check_btn:
                main_msg_tv.setText("");
                HashMap<String,String> map = new HashMap<>();
                map.put("type","yunda");
                map.put("postid","3549490005325");
//                map.put("mobile","18328008870");
//                map.put("secret","34ba01d602c88790bbe81a7aca8d3a9f");
//                KLog.e("mobile:  "+"18328008870"+"  secret:   "+"34ba01d602c88790bbe81a7aca8d3a9f");
                present.login(map,true,true);
                break;
            case R.id.main_check2_btn:
                main_msg_tv.setText("");
                HashMap<String,String> map2 = new HashMap<>();
                map2.put("type","yuantong");
                map2.put("postid","800348710785747640");
                present.logout(map2,false,true);
                break;
        }
    }

    @Override
    protected LoginPresenter bindPresent() {
        return new LoginPresenter(this,this);
    }

    @Override
    protected void initView() {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.frame_lay, new LoginFragment()).
                commitAllowingStateLoss();
    }

    @Override
    protected int setContentViewBody() {
        return R.layout.activity_main;
    }

    @Override
    public void onSuccess(ApiAction action, Object o) {
        if (action.equals(ApiAction.LOGIN)){
            List<Login> logins = (List<Login>) o;
            LogUtil.e(logins.toString());
            main_msg_tv.setText(logins.toString());
        }else if (action.equals(ApiAction.LOGOUT)){
            List<Login> logins = (List<Login>) o;
            LogUtil.e(logins.toString());
            main_msg_tv.setText(logins.toString());
        }
    }

    @Override
    public void onError(ApiAction action, int code, String message) {
        ToastUtil.showLongToast("code:" + code + "message:" + message);
    }

    @Override
    public void onStartUp(ApiAction action) {

    }

    @Override
    public void onCompleted(ApiAction action) {

    }

}
