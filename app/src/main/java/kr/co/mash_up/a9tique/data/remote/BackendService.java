package kr.co.mash_up.a9tique.data.remote;

import com.google.gson.JsonObject;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
        수정(판매자 등록)
    판매자 정보
        수정
 */
public interface BackendService {

    /****************** Category ********************/
    /**
     * 카테고리 목록 조회
     *
     * @return 카테고리 목록
     */
    @GET("api/category")
    Observable<JsonObject> getCategories();

    /****************** Product ********************/
    /**
     * 상품 정보 추가
     *
     * @param requestProduct 추가할 상품 정보
     * @return 성공 or 실패
     */
    @Multipart
    @POST("api/product")
    Observable<JsonObject> addProduct(@Body RequestProduct requestProduct);

    /**
     * 상품 정보 삭제
     *
     * @param productId 삭제할 상품 id
     * @return 성공 or 실패
     */
    @DELETE("api/product/{id}")
    Observable<JsonObject> deleteProduct(@Path("id") long productId);

    /**
     * 상품 정보 수정
     *
     * @param productId      수정할 상품 id
     * @param requestProduct 수정할 상품 정보
     * @return 성공 or 실패
     */
    @PUT("api/product/{id}")
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
    @GET("api/product")
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
    @GET("api/product/{id}")
    Observable<JsonObject> getProductDetail(@Path("id") long productId);

    /****************** User ********************/
    /**
     * 로그인
     *
     * @param user oauth token, type 정보
     * @return access token, user level
     */
    @POST("login")
    Observable<JsonObject> login(@Body RequestUser user);

    //Todo: /api/user/{id} - 판매자 권한 획득 정의

    /****************** Zzim ********************/
    /**
     * 찜 목록에 상품 추가
     *
     * @param productId 추가할 상품 id
     * @return 성공 or 실패
     */
    @FormUrlEncoded
    @POST("api/zzim")
    Observable<JsonObject> addZzimProduct(@Field("product_id") long productId);

    /**
     * 찜 목록 조회
     *
     * @param pageNo   이번에 요청할 page number
     * @param pageSize page에 담긴 아이템 수
     * @return status code, data
     */
    @GET("api/zzim")
    Observable<JsonObject> getZzimProducts(@Query("pageNo") int pageNo,
                                           @Query("pageSize") int pageSize);

    /**
     * 찜 목록에서 상품 삭제
     *
     * @param productId 삭제할 상품 id
     * @return 성공 or 실패
     */
    @DELETE("api/zzim/product/{id}")
    Observable<JsonObject> deleteZzimProduct(@Path("id") long productId);
}
