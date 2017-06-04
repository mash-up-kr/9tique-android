package kr.co.mash_up.a9tique.util.ui;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by seokjunjeong on 2017. 5. 27..
 */

public class FragmentUtil {

    public static Fragment getFragment(@NonNull FragmentActivity fragmentActivity,
                                       @IdRes int layoutId) {
        return fragmentActivity
                .getSupportFragmentManager()
                .findFragmentById(layoutId);
    }

    public static void addFragment(@NonNull FragmentActivity fragmentActivity,
                                   @IdRes int layoutId,
                                   Fragment fragment) {
        if (getFragment(fragmentActivity, layoutId) == null) {
            FragmentTransaction transaction =
                    fragmentActivity.getSupportFragmentManager()
                            .beginTransaction();
            transaction.add(layoutId, fragment, fragment.getTag());
            transaction.commit();
        }
    }

    public static void replaceFragment(@NonNull FragmentActivity fragmentActivity,
                                       @IdRes int layoutId,
                                       Fragment fragment) {
        if (getFragment(fragmentActivity, layoutId) != null) {
            FragmentTransaction transaction =
                    fragmentActivity.getSupportFragmentManager()
                            .beginTransaction();
            transaction.replace(layoutId, fragment, fragment.getTag());
            transaction.commit();
        }
    }
}
