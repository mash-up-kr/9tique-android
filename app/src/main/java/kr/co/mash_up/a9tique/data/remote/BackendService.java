package kr.co.mash_up.a9tique.data.remote;

import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/*
    카테고리
        리스트 o
    상품
        리스트 o
        추가 o
        삭제 o
        상세정보 o
        수정 o
    찜
        추가 o
        삭제 o
        리스트 o
    유저
        추가(로그인) o
        수정(판매자 등록) o
    판매자 정보 o
        수정 o
 */
public interface BackendService {

    /******************
     * Product Image
     ********************/

    @Multipart
    @POST("api/product_images")
    Observable<JsonObject> addProductImage(@Part List<MultipartBody.Part> imageFiles);

    /****************** Product ********************/
    /**
     * 상품 정보 추가
     *
     * @param requestProduct 추가할 상품 정보
     * @return 성공 or 실패
     */
    @POST("api/products")
    Observable<JsonObject> addProduct(@Body RequestProduct requestProduct);

    /**
     * 상품 정보 삭제
     *
     * @param productId 삭제할 상품 id
     * @return 성공 or 실패
     */
    @DELETE("api/products/{id}")
    Observable<JsonObject> deleteProduct(@Path("id") long productId);

    /**
     * 상품 정보 수정
     *
     * @param productId      수정할 상품 id
     * @param requestProduct 수정할 상품 정보
     * @return 성공 or 실패
     */
    @PUT("api/products/{id}")
    Observable<JsonObject> updateProduct(@Path("id") long productId,
                                         @Body RequestProduct requestProduct);

    /**
     * 카테고리별 상품 목록 조회
     * ex. product?pageNo=0&pageSize=2&mainCategory=shose
     *
     * @param pageNo       이번에 요청할 page number
     * @param pageSize     page에 담긴 아이템 수
     * @param mainCategory 메인 카테고리
     * @param subCategory  서브 카테고리
     * @return 카테고리별 상품 목록
     */
    @GET("api/products")
    Observable<JsonObject> getProducts(@Query("pageNo") int pageNo,
                                       @Query("pageSize") int pageSize,
                                       @Query("mainCategory") String mainCategory,
                                       @Query("subCategory") String subCategory);

    /**
     * 상품 상세정보 조회
     *
     * @param productId 상세정보를 볼 상품 id
     * @return 상세정보
     */
    @GET("api/products/{id}")
    Observable<JsonObject> getProductDetail(@Path("id") long productId);

    /****************** User ********************/
    /**
     * 로그인
     *
     * @param user oauth token, type 정보
     * @return access token, user level
     */
    @POST("api/users/login")
    Observable<JsonObject> login(@Body RequestUser user);

    @GET("api/users/refresh")
    Observable<JsonObject> refreshAccessToken();

    /**
     * 판매자 권한 획득
     *
     * @return access token, user level
     */
    @PUT("api/sellers/register")
    Observable<JsonObject> registerSeller(@Body RequestAuthenticationCode authenticationCode);

    /****************** Zzim ********************/
    /**
     * 찜 목록에 상품 추가
     *
     * @param productId 추가할 상품 id
     * @return 성공 or 실패
     */
    @FormUrlEncoded
    @POST("api/zzims")
    Observable<JsonObject> addZzimProduct(@Field("product_id") long productId);

    /**
     * 찜 목록 조회
     *
     * @param pageNo   이번에 요청할 page number
     * @param pageSize page에 담긴 아이템 수
     * @return status code, data
     */
    @GET("api/zzims")
    Observable<JsonObject> getZzimProducts(@Query("pageNo") int pageNo,
                                           @Query("pageSize") int pageSize);

    /**
     * 찜 목록에서 상품 삭제
     *
     * @param productId 삭제할 상품 id
     * @return 성공 or 실패
     */
    @DELETE("api/zzims/product/{id}")
    Observable<JsonObject> deleteZzimProduct(@Path("id") long productId);


    @GET("api/sellers/info")
    Observable<JsonObject> getSellerInfo();

    @PUT("api/sellers")
    Observable<JsonObject> updateSellerInfo(@Body RequestSeller requestSeller);

    /**
     * 판매자가 등록한 상품 목록 조회
     * ex. products?pageNo=0&pageSize=2
     *
     * @param pageNo   이번에 요청할 page number
     * @param pageSize page에 담긴 아이템 수
     * @return 판매자가 등록한 상품 목록
     */
    @GET("api/sellers/products")
    Observable<JsonObject> getSellProducts(@Query("pageNo") int pageNo,
                                           @Query("pageSize") int pageSize);

    /**
     * 판매자가 등록한 상품 선택삭제
     * DELETE method에 Body를 넣는건 Http Spec을 준수하는 Retrofit에서 지원하지 않아 트릭을 사용
     *
     * @return 성공 or 실패
     */
    @HTTP(path = "api/sellers/products", method = "DELETE", hasBody = true)
    Observable<JsonObject> deleteSellProducts(@Body RequestDeleteProduct product);
}
