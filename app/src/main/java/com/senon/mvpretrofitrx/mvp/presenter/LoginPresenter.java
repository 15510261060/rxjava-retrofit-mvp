package com.senon.mvpretrofitrx.mvp.presenter;

import com.example.libcore.mvp.present.BasePresenter;
import com.example.libcore.reference.weakreference.WeakReferenceContext;
import com.senon.mvpretrofitrx.mvp.model.LoginModel;
import com.senon.mvpretrofitrx.mvp.presenter.ipresent.ILoginPresent;
import com.senon.mvpretrofitrx.mvp.view.ApiAction;
import com.senon.mvpretrofitrx.mvp.view.iview.ILoginView;
import com.trello.rxlifecycle.components.support.RxFragmentActivity;

import java.util.HashMap;

/**
 * 作者：hebin
 *
 */
public class LoginPresenter extends BasePresenter implements ILoginPresent {

    private LoginModel model;

    public LoginPresenter(RxFragmentActivity reference, ILoginView view) {
        super(new WeakReferenceContext(reference), view);
        model = LoginModel.getInstance();
    }

    public void login(HashMap<String, String> map, boolean isDialog, boolean cancelable) {
        RxFragmentActivity rxFragmentActivity = get(RxFragmentActivity.class);
        model.login(map, createProgressSubscriber(rxFragmentActivity, ApiAction.LOGIN, mView, isDialog, cancelable), rxFragmentActivity);

    }

    public void logout(HashMap<String, String> map, boolean isDialog, boolean cancelable) {
        RxFragmentActivity rxFragmentActivity = get(RxFragmentActivity.class);
        model.logout(map, createProgressSubscriber(rxFragmentActivity, ApiAction.LOGOUT, mView, isDialog, cancelable), rxFragmentActivity);
    }

}
