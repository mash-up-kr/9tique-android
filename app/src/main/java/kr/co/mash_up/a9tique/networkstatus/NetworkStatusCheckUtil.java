package kr.co.mash_up.a9tique.networkstatus;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkStatusCheckUtil {

    enum Type {
        NOT_CONNECTED,
        WIFI,
        MOBILE
    }

    public static Type getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return Type.WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return Type.MOBILE;
        }
        return Type.NOT_CONNECTED;
    }

    public static boolean isConnectivityStatus(Context context) {
        Type type = getConnectivityStatus(context);
        return type != Type.NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        Type type = NetworkStatusCheckUtil.getConnectivityStatus(context);

        String status = null;
        if (type == Type.MOBILE) {
            status = "Mobile data enabled";
        } else if (type == Type.WIFI) {
            status = "Wifi enabled";
        } else if (type == Type.NOT_CONNECTED) {
            status = "Not connected to internet";
        }
        return status;
    }
}
