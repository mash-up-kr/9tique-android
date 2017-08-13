package kr.co.mash_up.a9tique.data.source.remote;

import com.google.gson.JsonObject;

import kr.co.mash_up.a9tique._old.data.remote.RequestProduct;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 상품 API에 대한 정보
 * <p>
 * Created by ethankim on 2017. 8. 12..
 */

public interface ProductService {

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
}
