package kr.co.mash_up.a9tique.common;

/**
 * Created by Dong on 2016-12-13.
 */

public final class Constants {

    // network
    public static final String END_POINT = "http://192.168.0.4:8080/";
    public static final int CONNECT_TIMEOUT = 10;
    public static final int READ_TIMEOUT = 10;
    public static final int WRITE_TIMEOUT = 10;
    public static final String USER_AGENT = "User-Agent";
    public static final String AUTHORIZATION = "Authorization";
    public static final String PREFIX_ACCESS_TOKEN = "Bearer ";

    // SharedPreference
    public static final String PREF_ACCESS_TOKEN = "Pref_Access_Token";
    public static final String PREF_USER_LEVEL = "Pref_User_Level";

    // Product api result
    public static final String API_RESULT = "apiResult";
    public static final int PRODUCT_UPDATE_STATUS_SUCCESS = 3000;
    public static final int PRODUCT_UPDATE_STATUS_FAILURE = 3001;
    public static final int PRODUCT_DELETE_SUCCESS = 3002;
    public static final int PRODUCT_DELETE_FAILURE = 3003;
    public static final int PRODUCT_UPDATE_SUCCESS = 3004;
    public static final int PRODUCT_UPDATE_FAILURE = 3005;
    public static final int PRODUCT_CREATE_SUCCESS = 3006;
    public static final int PRODUCT_CREATE_FAILURE = 3007;

    public static final String TRANSITION_NAME = "transitionName";

    // Product
    public static final String PRODUCT = "product";
    public static final String PRODUCT_ID = "productId";
    public static final String PRODUCT_IMAGE_TRANSITION = "product_image_";

    public static final String SELLER = "seller";
}
