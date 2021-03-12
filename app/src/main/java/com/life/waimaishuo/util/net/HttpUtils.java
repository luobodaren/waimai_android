package com.life.waimaishuo.util.net;

import com.life.base.utils.GsonUtil;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.Global;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
    final static private String ERROR01="请求失败！";
    final static private String ERROR02="请求异常！";

    final static private String MEDIA_TYPE_FORMDATA = "multipart/form-data; charset=utf-8";
    final static private String MEDIA_TYPE_JSON = "application/json";
    final static private String MEDIA_TYPE_TXT = "text/plain; charset=utf-8";
    final static private String MEDIA_TYPE_XML = "application/xml";
    final static private String MEDIA_TYPE_PNG = "image/png";
    final static private String MEDIA_TYPE_JPG = "image/jpeg";
    final static private String MEDIA_TYPE_GIF = "image/gif";

    private static String TOKEN_KEY = "Authorization-cjwm-app";

    private static Long READ_TIME_OUT = 5L;   //从主机读取数据超时时间（秒）
    private static Long WRITE_TIME_OUT = 5L;
    private static Long CONNECTED_TIME_OUT = 5L;  //连接主机超时时间（秒）

    //单例模式（饿汉式），饿汉式就是提前创建好封装类，还有懒汉式，我的其他博客有详细讲解
    private static HttpUtils httpUtils = new HttpUtils();
    private HttpUtils(){}

    //使本类唯一，只能允许一处线程进入，不允许有第二处线程进入
    public synchronized static HttpUtils getHttpUtils(){
        if (httpUtils==null){
            httpUtils=new HttpUtils();
        }
        return httpUtils;
    }

    /**
     * GET请求
     * @param url
     * @param isWithToken 带Token
     * @param httpCallback
     */
    public void doGet(String url, boolean isWithToken, final HttpCallback httpCallback){
        //第一步创建OkHttpClient对象
        final OkHttpClient okHttpClient = new OkHttpClient();

        //第二步创建request
        Request.Builder builder;
        if(isWithToken){
            builder = getRequestBuild(true);
        }else{
            builder = getRequestBuild(false);
        }
        final Request request = builder.url(url).get().build();

/*        //5.同步请求网络execute()
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    if(response.isSuccessful()){
                        LogUtil.e("Benner请求成功同步="+response.body().string());
                    }else{
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();*/

        //异步请求网络enqueue(Callback)
        executeQuest_Async(okHttpClient, request, httpCallback);
    }

    /**
     * POST请求（键值对 key-value）
     * @param url
     * @param params
     * @param isWithToken 带token
     * @param httpCallback
     */
    public void doPostForm(String url, Map<String,String> params, boolean isWithToken,final HttpCallback httpCallback){
        // 1.拿到okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.创建 FormBody 添加需要的键值对
        FormBody formBody = parseMapToFormBody(params);

        // 3.构造Request
        Request.Builder builder;
        if(isWithToken){
            builder = getRequestBuild(true);
        }else{
            builder = getRequestBuild(false);
        }
        Request request = builder.url(url)
                .post(formBody)//键值对
                .build();

        //异步请求enqueue(Callback)
        executeQuest_Async(okHttpClient, request, httpCallback);
    }

    /**
     * Post请求（json字符串）
     * @param url
     * @param jsonStr
     * @param isWithToken 带token
     * @param httpCallback
     */
    public void doPostJson(String url, String jsonStr, boolean isWithToken, final HttpCallback httpCallback) {
        // 1.拿到okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.创建 RequestBody 设置提交类型MediaType+json字符串
        RequestBody requestBody =  RequestBody.create(jsonStr, MediaType.parse(MEDIA_TYPE_JSON));

        // 3.构造Request
        Request.Builder builder;
        if(isWithToken){
            builder = getRequestBuild(true);
        }else{
            builder = getRequestBuild(false);
        }
        Request request = builder.url(url).post(requestBody)
                .build();

        //异步请求enqueue(Callback)
        executeQuest_Async(okHttpClient, request, httpCallback);
    }

    /**
     * 提交txt文件
     * POST请求
     * @param url
     * @param filePath
     * @param isWithToken
     * @param httpCallback
     */
    public void requestPostFileTxt(String url, String filePath, boolean isWithToken, HttpCallback httpCallback){

        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.获取文件地址，设置上传文件类型，构造RequestBody对象
        File fileAddress = new File(filePath);
        MediaType mediaType = MediaType.parse(MEDIA_TYPE_TXT);
        RequestBody requestBody = RequestBody.create(fileAddress,mediaType);

        //3.构造Requst对象
        Request.Builder builder;
        if(isWithToken){
            builder = getRequestBuild(true);
        }else{
            builder = getRequestBuild(false);
        }
        Request request = builder.url(url).post(requestBody).build();

        //4.构造Call对象进行 异步请求enqueue(Callback)
        executeQuest_Async(okHttpClient, request, httpCallback);

    }

    /**
     * 上传jpg图片
     * @param url
     * @param filePath
     * @param isWithToken
     * @param httpCallback
     */
    public void requestPostJPG(String url, String filePath, boolean isWithToken, final HttpCallback httpCallback){
        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.获取文件地址，设置上传文件类型，构造RequestBody对象
        File fileAddress = new File(filePath);
        MediaType mediaType = MediaType.parse(MEDIA_TYPE_JPG);
        RequestBody requestBody = RequestBody.create(fileAddress,mediaType);

        //3.构造Requst对象
        Request.Builder builder;
        if(isWithToken){
            builder = getRequestBuild(true);
        }else{
            builder = getRequestBuild(false);
        }
        Request request = builder.url(url).post(requestBody).build();

        //4.构造Call对象进行 异步请求enqueue(Callback)
        executeQuest_Async(okHttpClient, request, httpCallback);
    }

    /**
     * 上传jpg图片
     * @param url
     * @param filePath
     * @param isWithToken
     * @param httpCallback
     */
    public void requestPostPNG(String url, String filePath, boolean isWithToken, final HttpCallback httpCallback){
        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.获取文件地址，设置上传文件类型，构造RequestBody对象
        File fileAddress = new File(filePath);
        MediaType mediaType = MediaType.parse(MEDIA_TYPE_PNG);
        RequestBody requestBody = RequestBody.create(fileAddress,mediaType);

        //3.构造Requst对象
        Request.Builder builder;
        if(isWithToken){
            builder = getRequestBuild(true);
        }else{
            builder = getRequestBuild(false);
        }
        Request request = builder.url(url).post(requestBody).build();

        //4.构造Call对象进行 异步请求enqueue(Callback)
        executeQuest_Async(okHttpClient, request, httpCallback);
    }

    /**
     * 多文件及表单上传
     * @param url
     * @param signPath
     * @param params
     * @param pathList
     * @param httpCallback
     */
    public void upLoadFiles(final String url, final String signPath, final Map<String, Object> params, final List<String> pathList, HttpCallback httpCallback) {
        LogUtil.w("url:\n" + url);
        LogUtil.w("请求参数 params:\n" + params.toString());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()  //创建MultipartBody
                .setType(MultipartBody.FORM);

        //添加表单信息
        RequestBody bodyParams = RequestBody
                .create(MediaType.parse(MEDIA_TYPE_FORMDATA), GsonUtil.gsonString(params));
        requestBodyBuilder .addFormDataPart("", "", bodyParams);// FIXME: 2020/11/23 修改表单key value

        //循环添加文件
        for (int i = 0; i < pathList.size(); i++) {
            File file = new File(pathList.get(i));
            requestBodyBuilder.addFormDataPart("imgs", file.getName()
                    , RequestBody.create(new File(pathList.get(i))
                            ,MediaType.parse(MEDIA_TYPE_FORMDATA)));// FIXME: 2020/11/23 修改name值 不可相同
        }
        RequestBody requestBody = requestBodyBuilder.build();
        Request request = getRequestBuild(true)
                .url( url)
                .post(requestBody)
                .build();
        executeQuest_Async(okHttpClient,request,httpCallback);
    }

    /**
     * 获得Request的Builder对象
     * @param isWithToken 带token
     * @return
     */
    private Request.Builder getRequestBuild(boolean isWithToken){
        Request.Builder builder = new Request.Builder();
        if(isWithToken){
            builder.header(TOKEN_KEY, Global.getToken());
        }
        return builder;
    }

    /**
     * 拼接参数
     * @param url
     * @param params
     * @return
     */
    public String appendParams(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url + "?");
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private void executeQuest_Async(OkHttpClient okHttpClient, Request request, final HttpCallback httpCallback){
        okHttpClient.newBuilder()
                .connectTimeout(CONNECTED_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT,TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .build().newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtil.e("error:" + e.getMessage() + " url:" + request.url().toString());
                        httpCallback.onError(HttpCallback.ERROR_TYPE_REQUEST, e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json = response.body().string();
                        try {
                            Integer code = GsonUtil.getIntNoteJsonString(json,"code");
                            if(code != null && code == 0){
                                String data = GsonUtil.getStringNoteJsonString(json,"data");
                                LogUtil.d("data:" + data);
                                if(data == null || "null".equals(data)){    //有字段但返回null
                                    data = "";
                                }
                                httpCallback.onSuccess(data);
                            }else{
                                httpCallback.onError(HttpCallback.ERROR_TYPE_CODE, new Error("失败，responseJson:" + json));
                            }
                        }catch (Exception e){
                            LogUtil.e("error:" + e.getMessage() + " url:" + request.url().toString() + " resultJson:" + json);
                            httpCallback.onError(HttpCallback.ERROR_TYPE_PARSE, new Error("解析失败"));
                        }
                    }
                });
    }

    /**
     * 解析Map数组，生成简单key-value的FormBody
     * @param params
     * @return
     */
    private FormBody parseMapToFormBody(Map<String,String> params){
        FormBody.Builder build = new FormBody.Builder();
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                String value = params.get(key);
                if(value != null)
                    build.add(key,value);
            }
        }
        return build.build();
    }

    //创建接口
    public interface HttpCallback{
        //请求失败状态
        int ERROR_TYPE_REQUEST = 0;
        //code错误
        int ERROR_TYPE_CODE = 1;
        //json解析错误
        int ERROR_TYPE_PARSE = 2;

        //请求成功时的监听方法
        void onSuccess(String data);

        /**
         * 请求失败时的监听方法
         * @param type
         * @param error
         */
        void onError(int type, Throwable error);
    }

    public static String changeToHttps(String url){
        if(url == null || "".equals(url)){
            return "";
        }
        if(url.startsWith("http")){
            if(url.startsWith("http:")){
                String[] strings = url.split(":",2);
                return "https:" + strings[1];
            }else{
                return url;
            }
        }
        return "";
    }


}
