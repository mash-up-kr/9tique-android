package kr.co.mash_up.a9tique.data.remote;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import kr.co.mash_up.a9tique.BuildConfig;
import kr.co.mash_up.a9tique.common.Constants;
import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.data.User;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
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
                .addInterceptor(interceptor)  // http 로깅 설정
                .addInterceptor(new AccessTokenHttpInterceptor())
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
        Observable<JsonObject> call = service.getCategories();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jaRoot -> {

                    if (jaRoot != null) {
                        Log.e(TAG, jaRoot.toString());

//                        callback.onSuccess();
                    } else {
                        callback.onFailure();
                    }

                }, throwable -> {
                    Log.e(TAG, "getCategories " + throwable.getMessage());
                });
    }

    public void login(RequestUser user, ResultCallback<User> callback) {
        Observable<JsonObject> call = service.login(user);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> {
                    Integer statusCode = jsonObject.get("status").getAsInt();
                    Log.d(TAG, "status code: " + statusCode);

                    if (statusCode / 100 == 2) {
                        User resUser = new Gson().fromJson(jsonObject, User.class);

                        Log.d(TAG, resUser.getAccessToken() + " " + resUser.getUserLevel());

                        callback.onSuccess(resUser);
                    } else {
                        callback.onFailure();
                    }
                }, throwable -> {
                    Log.e(TAG, "login " + throwable.getMessage());
                    callback.onFailure();
                });
    }

    public void registerSeller(ResultCallback<User> callback) {
        Observable<JsonObject> call = service.registerSeller();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> {
                    Integer statusCode = jsonObject.get("status").getAsInt();
                    Log.d(TAG, "status code: " + statusCode);

                    if (statusCode / 100 == 2) {
                        User resUser = new Gson().fromJson(jsonObject, User.class);

                        Log.d(TAG, resUser.getAccessToken() + " " + resUser.getUserLevel());

                        callback.onSuccess(resUser);
                    } else {
                        callback.onFailure();
                    }
                }, throwable -> {
                    Log.e(TAG, "register seller " + throwable.getMessage());
                    callback.onFailure();
                });
    }

    public void getProducts(int pageNo, String mainCategory, String subCategory, ResultCallback<ResponseProduct> callback) {
        Observable<JsonObject> call = service.getProducts(pageNo, 20, mainCategory, subCategory);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> {
                    int statusCode = jsonObject.get("status").getAsInt();
                    Log.d(TAG, "status code: " + statusCode);

                    if (statusCode / 100 == 2) {
                        int currentPageNo = jsonObject.get("page_no").getAsInt();
                        int pageTotal = jsonObject.get("page_total").getAsInt();

                        List<Product> products = new ArrayList<Product>();
                        JsonArray jsonArray = jsonObject.getAsJsonArray("list");
                        Product product;
                        for (int i = 0; i < jsonArray.size(); i++) {
                            product = new Gson().fromJson(jsonArray.get(i).getAsJsonObject(), Product.class);
                            Log.d(TAG, "product: " + product.toString());
                            products.add(product);
                        }

                        callback.onSuccess(new ResponseProduct(products, currentPageNo, pageTotal));
                    } else {
                        callback.onFailure();
                    }

                }, throwable -> {
                    Log.e(TAG, "getProducts " + throwable.getMessage());
                    callback.onFailure();
                });
    }

    public void addProduct(Map<String, RequestBody> requestProduct, List<MultipartBody.Part> imageFiles, ResultCallback callback) {
        Observable<JsonObject> call = service.addProduct(requestProduct, imageFiles);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> {
                    int statusCode = jsonObject.get("status").getAsInt();
                    Log.d(TAG, "status code: " + statusCode);

                    if (statusCode / 100 == 2) {
                        callback.onSuccess(null);
                    } else {
                        callback.onFailure();
                    }
                }, throwable -> {
                    Log.e(TAG, "addProduct " + throwable.getMessage());
                    callback.onFailure();
                });
    }

    public void updateProduct(long productId, RequestProduct requestProduct, ResultCallback callback) {
        Observable<JsonObject> call = service.updateProduct(productId, requestProduct);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> {
                    int statusCode = jsonObject.get("status").getAsInt();
                    Log.d(TAG, "status code: " + statusCode);

                    if (statusCode / 100 == 2) {
                        callback.onSuccess(null);
                    } else {
                        callback.onFailure();
                    }
                }, throwable -> {
                    Log.e(TAG, "updateProduct " + throwable.getMessage());
                    callback.onFailure();
                });
    }

    public void deleteProduct(long productId, ResultCallback callback) {
        Observable<JsonObject> call = service.deleteProduct(productId);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> {
                    int statusCode = jsonObject.get("status").getAsInt();
                    Log.d(TAG, "status code: " + statusCode);

                    if (statusCode / 100 == 2) {
                        callback.onSuccess(null);
                    } else {
                        callback.onFailure();
                    }
                }, throwable -> {
                    Log.e(TAG, "deleteProduct " + throwable.getMessage());
                    callback.onFailure();
                });
    }
}
