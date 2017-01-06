package kr.co.mash_up.a9tique.util;

import java.util.HashMap;

import kr.co.mash_up.a9tique.data.MainCategory;
import kr.co.mash_up.a9tique.data.SubCategory;

/**
 * 한글로된 카테고리를 영어로 변환해주는 유틸 클래스
 */
public class TranslationUtil {

    private static TranslationUtil instance;
    private HashMap<String, String> translationMap;

    public static TranslationUtil getInstance() {
        if (instance == null) {
            synchronized (TranslationUtil.class) {
                if (instance == null) {
                    instance = new TranslationUtil();
                }
            }
        }
        return instance;
    }

    private TranslationUtil() {
        translationMap = new HashMap<>();
        translationMap.put("아우터", MainCategory.List.OUTER.name());
        translationMap.put("상의", MainCategory.List.TOP.name());
        translationMap.put("하의", MainCategory.List.BOTTOM.name());
        translationMap.put("신발", MainCategory.List.SHOSE.name());
        translationMap.put("모자", MainCategory.List.CAP.name());
        translationMap.put("자켓", SubCategory.List.JACKET.name());
        translationMap.put("코트", SubCategory.List.COATS.name());
        translationMap.put("점퍼", SubCategory.List.JUMPER.name());
        translationMap.put("후드집업", SubCategory.List.HOODS_ZIPUP.name());
        translationMap.put("패딩점퍼", SubCategory.List.PADDING_JUMPER.name());
        translationMap.put("베스트", SubCategory.List.VEST.name());
        translationMap.put("티셔츠", SubCategory.List.TSHIRT.name());
        translationMap.put("후드티셔츠", SubCategory.List.HOODS_TSHIRT.name());
        translationMap.put("슬리브리스", SubCategory.List.SLEEVELESS.name());
        translationMap.put("셔츠", SubCategory.List.SHIRT.name());
        translationMap.put("니트", SubCategory.List.KNIT.name());
        translationMap.put("블라우스", SubCategory.List.BLOUSE.name());
        translationMap.put("원피스", SubCategory.List.ONE_PIECE.name());
        translationMap.put("데님팬츠", SubCategory.List.DENIM_PANTS.name());
        translationMap.put("팬츠", SubCategory.List.PANTS.name());
        translationMap.put("쇼츠", SubCategory.List.SHORTS.name());
        translationMap.put("스커트", SubCategory.List.SKIRT.name());
    }

    public String koreanToEnglish(String korean){
        return translationMap.get(korean);
    }
}
