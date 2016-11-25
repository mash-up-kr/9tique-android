package kr.co.mash_up.a9tique.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

/**
 * Created by Dong on 2016-11-23.
 */

public class PermissionCheckUtil {

    /*
      ContextCompat.checkSelfPermission(Context context, String permission)
      (액티비티, 조사할 퍼미션 이름)
      퍼미션을 가지고 있으면 PackageManager.PERMISSION_GRANTED 리턴
      없으면 PackageManager.PERMISSION_DENIED
      */
    @TargetApi(Build.VERSION_CODES.M)
    public static boolean checkPermission(@NonNull final Context context, @NonNull final String strPermission, @NonNull final int requestCode) {

        if (ContextCompat.checkSelfPermission(context, strPermission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    strPermission)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{strPermission},
                        requestCode);
            } else {
                /*
                사용자에게 퍼미션에 대해 설명
                최소 1번정도 거절했을 때 도움말 출력
                ActivityCompat.shouldShowRequestPermissionRationale()는 모든 상황을보고 설명이 필요한 시점이면 true 반환
                */
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, strPermission)) {
                    new AlertDialog.Builder(context)
                            .setMessage("이 프로그램이 원활하게 동작하기 위해서는 퍼미션을 허가가 꼭 필요합니다.")
                            .setTitle("권한 부여")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions((Activity) context, new String[]{strPermission}, requestCode);
                                }
                            })
                            .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                } else {  // permission not granted
                    /*
                    ActivityCompat.requestPermissions(Activity activity, String[] permissions, int requestCode)
                    필요한 퍼미션을 한꺼번에 요청하는데 2번째 인수로 퍼미션의 목록을 배열로 전달
                    요청할 퍼미션은 매니페스트에도 반드시 기록되어 있어야 한다.
                    */
                    ActivityCompat.requestPermissions((Activity) context, new String[]{strPermission}, requestCode);
                }
            }
            // permission deny
            return false;
        } else {
            // permission granted
            return true;
        }
    }
}
