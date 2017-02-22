package kr.co.mash_up.a9tique.common;


import android.support.annotation.NonNull;

import kr.co.mash_up.a9tique.data.User;

public class AccountManager {

    private static AccountManager sInstance;

    private String mAccessToken;

    private User.Level mLevel;

    public static AccountManager getInstance() {
        if (sInstance == null) {
            synchronized (AccountManager.class) {
                if (sInstance == null) {
                    sInstance = new AccountManager();
                }
            }
        }
        return sInstance;
    }

    private AccountManager() {
        this.mAccessToken = "";
        this.mLevel = User.Level.USER;
    }

    public void updateAccountInformation(@NonNull String accessToken, @NonNull String strUserLevel) {
        this.mAccessToken = accessToken;

        User.Level level = User.Level.USER;
        if ("SELLER".equals(strUserLevel)) {
            level = User.Level.SELLER;
        }
        this.mLevel = level;
    }

    public void clearAccountInformation() {
        this.mAccessToken = null;
        this.mLevel = null;
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public User.Level getLevel() {
        return mLevel;
    }
}