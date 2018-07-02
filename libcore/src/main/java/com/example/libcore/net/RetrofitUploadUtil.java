package com.example.libcore.net;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static okhttp3.MultipartBody.Builder;
import static okhttp3.MultipartBody.FORM;
import static okhttp3.MultipartBody.Part;

/**
 * Class description goes here.
 * <p>
 * Created by hebin on 17/8/23.
 */

public class RetrofitUploadUtil {
    /**
     * 将文件路径数组封装为{@link List<  Part  >}
     * @param key 对应请求正文中name的值。目前服务器给出的接口中，所有图片文件使用<br>
     * 同一个name值，实际情况中有可能需要多个
     * @param filePaths 文件路径数组
     * @param imageType 文件类型
     */
    public static List<Part> files2Parts(String key,
                                         String[] filePaths, MediaType imageType) {
        List<Part> parts = new ArrayList<>(filePaths.length);
        for (String filePath : filePaths) {
            File file = new File(filePath);
            // 根据类型及File对象创建RequestBody（okhttp的类）
            RequestBody requestBody = RequestBody.create(imageType, file);
            // 将RequestBody封装成MultipartBody.Part类型（同样是okhttp的）
            Part part = Part.
                    createFormData(key, file.getName(), requestBody);
            // 添加进集合
            parts.add(part);
        }
        return parts;
    }

    public static Map<String, Part> files2PartsMap(String key,
                                                   String[] filePaths, MediaType imageType) {
        Map<String, Part> parts = new HashMap<>();
        for (String filePath : filePaths) {
            File file = new File(filePath);
            // 根据类型及File对象创建RequestBody（okhttp的类）
            RequestBody requestBody = RequestBody.create(imageType, file);
            // 将RequestBody封装成MultipartBody.Part类型（同样是okhttp的）
            Part part = Part.
                    createFormData(key, file.getName(), requestBody);
            // 添加进集合
            parts.put(key,part);
        }
        return parts;
    }

    public static Map<String, RequestBody> files2RequestBodyMap(String key,
                                                                String[] filePaths, MediaType imageType) {
        Map<String, RequestBody> parts = new HashMap<>();
        for (String filePath : filePaths) {
            File file = new File(filePath);
            // 根据类型及File对象创建RequestBody（okhttp的类）
            RequestBody requestBody = RequestBody.create(imageType, file);
            // 添加进集合
            parts.put("file\"; filename=\"" + file.getName() + "",requestBody);
        }
        return parts;
    }

    /**
     * 其实也是将File封装成RequestBody，然后再封装成Part，<br>
     * 不同的是使用MultipartBody.Builder来构建MultipartBody
     * @param key 同上
     * @param filePaths 同上
     * @param imageType 同上
     */
    public static MultipartBody filesToMultipartBody(String key,
                                                     String[] filePaths,
                                                     MediaType imageType) {
        Builder builder = new Builder();
        for (String filePath : filePaths) {
            File file = new File(filePath);
            RequestBody requestBody = RequestBody.create(imageType, file);
            builder.addFormDataPart(key, file.getName(), requestBody);
        }
        builder.setType(FORM);
        return builder.build();
    }

    /**
     * 直接添加文本类型的Part到的MultipartBody的Part集合中
     * @param parts Part集合
     * @param key 参数名（name属性）
     * @param value 文本内容
     * @param position 插入的位置
     */
    public static void addTextPart(List<Part> parts,
                                   String key, String value, int position) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        Part part = Part.createFormData(key, null, requestBody);
        parts.add(position, part);
    }

    public static void addTextPart(Map<String, Part> parts,
                                   String key, String value, int position) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        Part part = Part.createFormData(key, null, requestBody);
        parts.put(key, part);
    }

    public static void addTextPartToRequestBody(Map<String, RequestBody> parts,
                                   String key, String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        parts.put(key, requestBody);
    }

    /**
     * 添加文本类型的Part到的MultipartBody.Builder中
     * @param builder 用于构建MultipartBody的Builder
     * @param key 参数名（name属性）
     * @param value 文本内容
     */
    public static Builder addTextPart(Builder builder,
                                      String key, String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        // MultipartBody.Builder的addFormDataPart()有一个直接添加key value的重载，但坑的是这个方法
        // 不会设置编码类型，会出乱码，所以可以使用3个参数的，将中间的filename置为null就可以了
        // builder.addFormDataPart(key, value);
        // 还有一个坑就是，后台取数据的时候有可能是有顺序的，比如必须先取文本后取文件，
        // 否则就取不到（真弱啊...），所以还要注意add的顺序
        builder.addFormDataPart(key, null, requestBody);
        return builder;
    }

}
