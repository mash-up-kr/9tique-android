package kr.co.mash_up.a9tique._old.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

/**
 * Created by Dong on 2017-02-14.
 */

public class ProgressUtil {

    @Nullable
    private static ProgressDialog sProgressDialog;

    private ProgressUtil() {
    }

    public static void showProgressDialog(@NonNull Context context) {
        showProgressDialog(context, null);
    }

    public static void showProgressDialog(@NonNull Context context, @StringRes int resourceId) {
        showProgressDialog(context, context.getString(resourceId));
    }

    public static void showProgressDialog(@NonNull Context context, String message) {
        if (sProgressDialog == null) {
            sProgressDialog = new ProgressDialog(context);
            sProgressDialog.setCancelable(false);
            sProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            sProgressDialog.setCanceledOnTouchOutside(false);  // 바깥쪽 눌러 종료 금지
        }
        if (message != null) {
            sProgressDialog.setMessage(message);
        }

        if (!sProgressDialog.isShowing()) {
            sProgressDialog.show();
        }
    }

    public static void hideProgressDialog() {
        if (sProgressDialog != null && sProgressDialog.isShowing()) {
            sProgressDialog.dismiss();
        }
        sProgressDialog = null;
    }
}
