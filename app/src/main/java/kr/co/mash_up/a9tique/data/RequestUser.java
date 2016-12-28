package kr.co.mash_up.a9tique.data;

import com.google.gson.annotations.SerializedName;


public class RequestUser {

    @SerializedName("oauth_token")
    private String oauthToken;

    @SerializedName("type")
    private Type type;

    public RequestUser(String oauthToken, Type type) {
        this.oauthToken = oauthToken;
        this.type = type;
    }

    public enum Type{
        KAKAO,
        FB
    }
}
