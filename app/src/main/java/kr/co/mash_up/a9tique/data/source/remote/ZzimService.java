package kr.co.mash_up.a9tique.data.source.remote;

import com.google.gson.JsonObject;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 찜 API에 대한 정보
 * <p>
 * Created by ethankim on 2017. 8. 12..
 */

public interface ZzimService {

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
}
