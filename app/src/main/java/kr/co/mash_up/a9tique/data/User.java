package kr.co.mash_up.a9tique.data;


import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("token")
    private String accessToken;

    @SerializedName("level")
    private String userLevel;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }
}
