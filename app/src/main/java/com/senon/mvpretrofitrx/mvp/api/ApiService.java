package com.senon.mvpretrofitrx.mvp.api;

import com.example.libcore.net.MAPIResult;
import com.senon.mvpretrofitrx.mvp.entity.Login;
import java.util.List;
import java.util.Map;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * api service
 */
public interface ApiService {

    @POST("query")
    Observable<MAPIResult<List<Login>>> login(@QueryMap Map<String, String> map);

    @POST("query")
    Observable<MAPIResult<List<Login>>> logout(@QueryMap Map<String, String> map);

//    // 登录的请求
//    @POST("loginManage/login")
//    Observable<BaseResponse<Login>> login(@QueryMap Map<String, String> map);

//    //上传图片
//    @POST("file/up")
//    @Multipart
//    Observable<BaseResponse<List<UploadFile>>> upload(@Part List<MultipartBody.Part> parts);


}
