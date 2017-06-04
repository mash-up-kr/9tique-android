package kr.co.mash_up.a9tique._old.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import kr.co.mash_up.a9tique._old.common.AccountManager;
import kr.co.mash_up.a9tique._old.data.User;
import kr.co.mash_up.a9tique._old.data.remote.BackendHelper;
import kr.co.mash_up.a9tique._old.data.remote.ResultCallback;

/**
 * 백그라운드에서 API서버와 통신해 Access Token을 갱신하는 서비스
 * 시스템에 의해 죽어도 다시 살아나 Access Token 갱신을 완료하고 스스로 종료
 */
public class TokenRefreshService extends Service {

    public TokenRefreshService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * 시스템에 의해 죽어도 다시 살아나게
     * 토큰 갱신을 마무리하고 스스로 종료되어야 한다
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*
        Todo: token 만료일을 확인하려면 key를 알아야하는데 클라이언트가 key를 가지고 있어야하는지 고민..
        확인하고 서버로 token refresh request
        */
        tokenRefresh();
        return START_STICKY;  // 재시작
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void tokenRefresh() {
        BackendHelper.getInstance().refreshAccessToken(new ResultCallback<User>() {
            @Override
            public void onSuccess(@Nullable User user) {
                AccountManager.getInstance().updateAccessToken(TokenRefreshService.this, user);
                stopSelf();  // 서비스 스스로 종료
            }

            @Override
            public void onFailure() {
                //Todo: token 갱신 실패했을 경우. 재귀호출. 맞는방법일까..?
                tokenRefresh();
            }
        });
    }
}