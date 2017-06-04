package kr.co.mash_up.a9tique._old.data.remote;

import com.google.gson.annotations.SerializedName;


public class RequestAuthenticationCode {

    @SerializedName("authenti_code")
    private String authenticationCode;

    public RequestAuthenticationCode(String authenticationCode) {
        this.authenticationCode = authenticationCode;
    }
}
