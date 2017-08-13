package kr.co.mash_up.a9tique.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Application에서 쓰이는 Gson을 제공한다
 * <p>
 * Created by ethankim on 2017. 8. 12..
 */

public class GsonUtils {

    private static Gson sGson;

    public static Gson getGson() {
        if (sGson == null) {
            sGson = new GsonBuilder()
                    .create();
        }

        return sGson;
    }
}
