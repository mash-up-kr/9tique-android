package kr.co.mash_up.a9tique.data.source.remote;

import com.google.gson.JsonObject;

import kr.co.mash_up.a9tique._old.data.remote.RequestAuthenticationCode;
import kr.co.mash_up.a9tique._old.data.remote.RequestUser;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import rx.Observable;

/**
 * 유저 API에 대한 정보
 * <p>
 * Created by ethankim on 2017. 8. 12..
 */

public interface UserService {

    /**
     * 로그인
     *
     * @param user oauth token, type 정보
     * @return access token, user level
     */
    @POST("api/users/login")
    Observable<JsonObject> login(@Body RequestUser user);

    /**
     * 토큰 갱신
     *
     * @return
     */
    @GET("api/users/refresh")
    Observable<JsonObject> refreshAccessToken();

    /**
     * 판매자 권한 획득
     *
     * @return access token, user level
     */
    @PUT("api/sellers/register")
    Observable<JsonObject> registerSeller(@Body RequestAuthenticationCode authenticationCode);
}
