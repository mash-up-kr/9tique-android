package kr.co.mash_up.a9tique.data.remote;

import com.google.gson.annotations.SerializedName;

public class RequestUser {

//    @SerializedName("oauth_token")
//    private String oauthToken;

    @SerializedName("name")
    private String userName;

    @SerializedName("email")
    private String email;

    @SerializedName("type")
    private OauthType oauthType;

    public RequestUser(String userName, String email, OauthType oauthType) {
        this.userName = userName;
        this.email = email;
        this.oauthType = oauthType;
    }

    public RequestUser(String oauthToken, OauthType oauthType) {
//        this.oauthToken = oauthToken;
        this.oauthType = oauthType;
    }

    public enum OauthType {
        KAKAO("kakao"),
        FB("fb");

        private String value;

        OauthType(String value) {
            this.value = value;
        }

        public String getKey(){
            return name();
        }

        public String getValue(){
            return this.value;
        }
    }
}