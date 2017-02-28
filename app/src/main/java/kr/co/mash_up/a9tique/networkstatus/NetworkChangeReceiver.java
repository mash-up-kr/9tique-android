package kr.co.mash_up.a9tique.networkstatus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import kr.co.mash_up.a9tique.common.eventbus.EventNetworkStatus;
import kr.co.mash_up.a9tique.common.eventbus.RxEventBus;

public class NetworkChangeReceiver extends BroadcastReceiver {

    public NetworkChangeReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")
//                || action.equals("android.net.wifi.WIFI_STATE_CHANGED")
                ) {

            if(!NetworkStatusCheckUtil.isConnectivityStatus(context)){
                RxEventBus.getInstance().post(new EventNetworkStatus());
            }
        }
    }
}