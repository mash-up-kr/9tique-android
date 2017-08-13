package kr.co.mash_up.a9tique.data.source.remote;

import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * 이미지 API에 대한 정보
 * <p>
 * Created by ethankim on 2017. 8. 12..
 */

public interface ImageService {

    @Multipart
    @POST("api/product_images")
    Observable<JsonObject> addProductImage(@Part List<MultipartBody.Part> imageFiles);
}
