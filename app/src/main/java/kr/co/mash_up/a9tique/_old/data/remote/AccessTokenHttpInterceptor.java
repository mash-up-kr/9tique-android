package kr.co.mash_up.a9tique._old.data.remote;

import java.io.IOException;

import kr.co.mash_up.a9tique.NinetiqueApplication;
import kr.co.mash_up.a9tique._old.common.Constants;
import kr.co.mash_up.a9tique._old.util.PreferencesUtils;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class AccessTokenHttpInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        String accessToken = PreferencesUtils.getString(NinetiqueApplication.getInstance(),
                Constants.PREF_ACCESS_TOKEN, "");

        Request request;

        if (accessToken != null && !"".equals(accessToken)) {
            request = chain.request().newBuilder()
                    .header("Accept", "application/json")
                    .header(Constants.AUTHORIZATION, Constants.PREFIX_ACCESS_TOKEN + accessToken)
                    .method(chain.request().method(), chain.request().body())
                    .build();
        } else {
            request = chain.request().newBuilder()
                    .header("Accept", "application/json")
                    .method(chain.request().method(), chain.request().body())
                    .build();
        }
        return chain.proceed(request);
    }
}
