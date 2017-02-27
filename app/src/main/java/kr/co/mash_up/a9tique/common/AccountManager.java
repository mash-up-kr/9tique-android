package kr.co.mash_up.a9tique.common;


import android.content.Context;
import android.support.annotation.NonNull;

import kr.co.mash_up.a9tique.data.User;
import kr.co.mash_up.a9tique.util.PreferencesUtils;

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

    public void initAccountInformation(String accessToken, String strUserLevel){
        this.mAccessToken = accessToken;
        User.Level level = User.Level.USER;
        if ("SELLER".equals(strUserLevel)) {
            level = User.Level.SELLER;
        }
        this.mLevel = level;
    }

    public void updateAccessToken(@NonNull Context context, @NonNull User user){
        String accessToken = user.getAccessToken();
        PreferencesUtils.putString(context, Constants.PREF_ACCESS_TOKEN, accessToken);
        this.mAccessToken = accessToken;
    }

    /**
     * 사용자 정보를 SharedPreferences와 AccountManager에 setting
     *
     * @param user setting할 사용자 정보
     */
    public void updateAccountInformation(@NonNull Context context, @NonNull User user) {
        String accessToken = user.getAccessToken();
        String strUserLevel = user.getUserLevel();

        PreferencesUtils.putString(context, Constants.PREF_ACCESS_TOKEN, accessToken);
        PreferencesUtils.putString(context, Constants.PREF_USER_LEVEL, strUserLevel);

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