package kr.co.mash_up.a9tique.common;


public class AccountManager {

    private static AccountManager instance;

    private String mKakaoId;

    public static AccountManager getInstance(){
        if(instance == null){
            synchronized (AccountManager.class){
                if(instance == null){
                    instance = new AccountManager();
                }
            }
        }
        return instance;
    }

    private AccountManager(){
        this.mKakaoId = "";
    }

    public String getKakaoId() {
        return mKakaoId;
    }

    public void setKakaoId(String kakaoId) {
        mKakaoId = kakaoId;
    }
}
