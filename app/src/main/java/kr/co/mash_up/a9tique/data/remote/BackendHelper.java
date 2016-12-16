package kr.co.mash_up.a9tique.data.remote;


import android.util.Log;

import com.google.gson.JsonArray;

import java.util.concurrent.TimeUnit;

import kr.co.mash_up.a9tique.BuildConfig;
import kr.co.mash_up.a9tique.common.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BackendHelper {

    public static final String TAG = BackendHelper.class.getSimpleName();

    private static BackendHelper instance;
    private BackendService service;

    public static BackendHelper getInstance() {
        if (instance == null) {
            synchronized (BackendHelper.class) {
                if (instance == null) {
                    instance = new BackendHelper();
                }
            }
        }
        return instance;
    }

    private BackendHelper() {
        OkHttpClient okHttpClient = makeOkHttpClient(makeLoggingInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        service = retrofit.create(BackendService.class);
    }

    /**
     * Make okHttp Client
     *
     * @param interceptor setting할 interceptor
     * @return interceptor setting된 OkHttpClient
     */
    private OkHttpClient makeOkHttpClient(HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .connectTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.SECONDS)  // 연결 타임아웃
                .readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)  // 읽기 타임아웃
                .writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.SECONDS)  // 쓰기 타임아웃
                .addInterceptor(interceptor)
                .build();
    }

    /**
     * Make http logging
     *
     * @return Level setting된 HttpLoggingInterceptor
     */
    private HttpLoggingInterceptor makeLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
                .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                        : HttpLoggingInterceptor.Level.NONE);
        return logging;
    }

    //Todo: implements
    public void getCategories(ResultCallback callback) {
        Observable<JsonArray> call = service.getCategories();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jaRoot -> {

                    if (jaRoot != null) {
                        Log.e(TAG, jaRoot.toString());

                        callback.onSuccess();
                    } else {
                        callback.onFailure();
                    }

                }, throwable -> {
                    Log.e(TAG, "getCategories " + throwable.getMessage());
                });
    }
}
