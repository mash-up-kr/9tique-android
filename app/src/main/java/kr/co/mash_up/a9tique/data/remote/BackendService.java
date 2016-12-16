package kr.co.mash_up.a9tique.data.remote;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import kr.co.mash_up.a9tique.data.Product;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/*
Todo: api interface 정의
    카테고리
        리스트 o
    상품
        추가 o
        삭제 o
        상세정보 o
        수정 o
        찜 x

Todo: access token 추가
 */
public interface BackendService {

    // 카테고리 리스트
    @Headers("Accept: application/json")
    @GET("category")
    Observable<JsonArray> getCategories();

    // 상품 추가 Todo: implement
    @Multipart
    @Headers("Accept: application/json")
    @POST("product")
    Observable<JsonObject> addProduct(
            @Field("name") String name,
            @Field("brand_name") String brandName,
            @Field("size") String size,
            @Field("price") int price,
            @Field("description") String description,
            @Field("status") Product.Status status,
            @Field("sellerId") long sellerId,  //Todo: token 인증으로 대체
            @Field("main_category") String mainCategory,
            @Field("sub_category") String subCategory,
            @Part("files") List<MultipartBody.Part> files
            );

    // 상품 삭제
    @Headers("Accept: application/json")
    @DELETE("product/{id}")
    Observable<JsonObject> deleteProduct(@Path("id") int productId);

    // 상품 수정
    @Headers("Accept: application/json")
    @PUT("product/{id}")
    Observable<JsonObject> updateProduct(@Path("id") int productId);

    // 상품 리스트
    // ex. product?pageNo=0&pageSize=2&mainCategory=shose
    @Headers("Accept: application/json")
    @GET("product")
    Observable<JsonArray> getProducts(@Query("pageNo") int pageNo,
                                @Query("pageSize") int pageSize,
                                @Query("mainCategory") String mainCategory,
                                @Query("subCategory") String subCategory);

    // 상품 상세정보
    @Headers("Accept: application/json")
    @GET("product/{id}")
    Observable<JsonObject> getProductDetail(@Path("id") int productId);
}
