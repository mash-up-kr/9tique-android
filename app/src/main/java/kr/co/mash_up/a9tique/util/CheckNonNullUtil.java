package kr.co.mash_up.a9tique.util;

import android.support.annotation.Nullable;

/**
 * Created by Dong on 2017-01-20.
 */

public class CheckNonNullUtil {

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }
}
