package kr.co.mash_up.a9tique.data.source.remote;

import com.google.gson.JsonObject;

import kr.co.mash_up.a9tique._old.data.remote.RequestDeleteProduct;
import kr.co.mash_up.a9tique._old.data.remote.RequestSeller;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 판매자 API 정보
 * <p>
 * Created by ethankim on 2017. 8. 12..
 */

public interface SellerService {

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
