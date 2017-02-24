package kr.co.mash_up.a9tique;

import android.app.Application;


public class NinetiqueApplication extends Application {

    private static NinetiqueApplication instance;

    /**
     * singleton 애플리케이션 객체를 얻는다.
     *
     * @return singleton 애플리케이션 객체
     */
    public static NinetiqueApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

//        KakaoSDK.init(new KakaoSDKAdapter());
    }

    /**
     * 애플리케이션 종료시 singleton 어플리케이션 객체 초기화한다.
     */
    @Override
    public void onTerminate() {
//        ProductsRepository.destroyInstance();
        instance = null;
        super.onTerminate();
    }

    /**
     * Session Config에 대해서는 default값들이 존재한다.
     * 필요한 상황에서만 override해서 사용하면 됨.
     *
     * @return Session의 설정값.
     */
//    class KakaoSDKAdapter extends KakaoAdapter {
//
//        @Override
//        public ISessionConfig getSessionConfig() {
//            return new ISessionConfig() {
//                @Override
//                public AuthType[] getAuthTypes() {
//                    // KaKaoTalk으로만 로그인
//                    return new AuthType[]{AuthType.KAKAO_TALK};
//                }
//
//                @Override
//                public boolean isUsingWebviewTimer() {
//                    return false;
//                }
//
//                @Override
//                public boolean isSecureMode() {
//                    return false;
//                }
//
//                @Override
//                public ApprovalType getApprovalType() {
//                    return ApprovalType.INDIVIDUAL;
//                }
//
//                @Override
//                public boolean isSaveFormData() {
//                    return true;
//                }
//            };
//        }
//
//        @Override
//        public IApplicationConfig getApplicationConfig() {
//            return NinetiqueApplication::getInstance;
//        }
//    }
}