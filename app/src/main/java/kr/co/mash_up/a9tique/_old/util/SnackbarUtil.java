package kr.co.mash_up.a9tique._old.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import kr.co.mash_up.a9tique.R;

/**
 * 스낵바 표시용 유틸
 */
public class SnackbarUtil {

    public static void showMessage(Context context, View view, String message, String actionMessage, @Nullable View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setAction(actionMessage, listener);
        ((TextView) snackbar
                .getView().findViewById(android.support.design.R.id.snackbar_text))
                .setTextColor(ContextCompat.getColor(context, R.color.white));
        snackbar.show();
    }
}
