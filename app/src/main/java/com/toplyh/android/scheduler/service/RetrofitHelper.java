package com.toplyh.android.scheduler.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.toplyh.android.scheduler.app.App;
import com.toplyh.android.scheduler.service.utils.SharedPreferencesUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 我 on 2017/11/15.
 */

public class RetrofitHelper {

    public static final String TAG="OkHttp";

    //短缓存1分钟
    public static final int CACHE_AGE_SHORT=60;
    //长缓存有效期1天
    public static final int CACHE_STALE_LONG=60*60*24;
    //base_url
    public static final String BASE_URL="http://192.168.1.102:8081/api/";


    private Context mContext;

    OkHttpClient client=new OkHttpClient();

    GsonConverterFactory factory;

    private static RetrofitHelper instance=null;


    private Retrofit mRetrofit=null;


    public static RetrofitHelper getInstance(Context context){
        if (instance==null){
            instance=new RetrofitHelper(context);
            if (context==null){
                Log.e(App.TAG,"context======null");
            }
        }
        return instance;
    }

    private RetrofitHelper(Context context){
        mContext=context;
        init();
    }

    private void init(){
        initOkHttpClient();
        resetApp();
    }

    private void resetApp(){
        Gson gson=new GsonBuilder().create();
        factory=GsonConverterFactory.create(gson);

        mRetrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


    }

    public RetrofitService getServer(){
        return mRetrofit.create(RetrofitService.class);
    }

    private void initOkHttpClient(){
        File cacheDirectory=new File(mContext.getCacheDir(),"HttpCache");
        Cache cache=new Cache(cacheDirectory,1024*1024*100);

        client=new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original=chain.request();
                        Request request=original.newBuilder()
                                .addHeader("Content-Type","application/json")
                                .addHeader("token",(String)SharedPreferencesUtils.getParam(mContext,"token",""))
                                .method(original.method(),original.body())
                                .build();
                        Log.e(App.TAG,(String)SharedPreferencesUtils.getParam(mContext,"token",""));
                        return chain.proceed(request);
                    }
                })
                .cache(cache)
                .build();
    }
}
