package kr.co.mash_up.a9tique._old.util;

import java.util.HashMap;

import kr.co.mash_up.a9tique._old.data.MainCategory;
import kr.co.mash_up.a9tique._old.data.SubCategory;

/**
 * 한글로된 카테고리를 영어로 변환해주는 유틸 클래스
 */
public class TranslationUtil {

    private static TranslationUtil instance;
    private HashMap<String, String> koreanToEnglishTranslationMap;
    private HashMap<String, String> englishToKoreanTranslationMap;

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
        koreanToEnglishTranslationMap = new HashMap<>();
        koreanToEnglishTranslationMap.put("아우터", MainCategory.List.OUTER.name());
        koreanToEnglishTranslationMap.put("상의", MainCategory.List.TOP.name());
        koreanToEnglishTranslationMap.put("하의", MainCategory.List.BOTTOM.name());
        koreanToEnglishTranslationMap.put("신발", MainCategory.List.SHOSE.name());
        koreanToEnglishTranslationMap.put("모자", MainCategory.List.CAP.name());
        koreanToEnglishTranslationMap.put("자켓", SubCategory.List.JACKET.name());
        koreanToEnglishTranslationMap.put("코트", SubCategory.List.COATS.name());
        koreanToEnglishTranslationMap.put("점퍼", SubCategory.List.JUMPER.name());
        koreanToEnglishTranslationMap.put("후드집업", SubCategory.List.HOODS_ZIPUP.name());
        koreanToEnglishTranslationMap.put("패딩점퍼", SubCategory.List.PADDING_JUMPER.name());
        koreanToEnglishTranslationMap.put("베스트", SubCategory.List.VEST.name());
        koreanToEnglishTranslationMap.put("티셔츠", SubCategory.List.TSHIRT.name());
        koreanToEnglishTranslationMap.put("후드티셔츠", SubCategory.List.HOODS_TSHIRT.name());
        koreanToEnglishTranslationMap.put("슬리브리스", SubCategory.List.SLEEVELESS.name());
        koreanToEnglishTranslationMap.put("셔츠", SubCategory.List.SHIRT.name());
        koreanToEnglishTranslationMap.put("니트", SubCategory.List.KNIT.name());
        koreanToEnglishTranslationMap.put("블라우스", SubCategory.List.BLOUSE.name());
        koreanToEnglishTranslationMap.put("원피스", SubCategory.List.ONE_PIECE.name());
        koreanToEnglishTranslationMap.put("데님팬츠", SubCategory.List.DENIM_PANTS.name());
        koreanToEnglishTranslationMap.put("팬츠", SubCategory.List.PANTS.name());
        koreanToEnglishTranslationMap.put("쇼츠", SubCategory.List.SHORTS.name());
        koreanToEnglishTranslationMap.put("스커트", SubCategory.List.SKIRT.name());

        englishToKoreanTranslationMap = new HashMap<>();
        englishToKoreanTranslationMap.put(MainCategory.List.OUTER.name(), "아우터");
        englishToKoreanTranslationMap.put(MainCategory.List.TOP.name(), "상의");
        englishToKoreanTranslationMap.put(MainCategory.List.BOTTOM.name(), "하의");
        englishToKoreanTranslationMap.put(MainCategory.List.SHOSE.name(), "신발");
        englishToKoreanTranslationMap.put(MainCategory.List.CAP.name(), "모자");
        englishToKoreanTranslationMap.put(SubCategory.List.JACKET.name(), "자켓");
        englishToKoreanTranslationMap.put(SubCategory.List.COATS.name(), "코트");
        englishToKoreanTranslationMap.put(SubCategory.List.JUMPER.name(), "점퍼");
        englishToKoreanTranslationMap.put(SubCategory.List.HOODS_ZIPUP.name(), "후드집업");
        englishToKoreanTranslationMap.put(SubCategory.List.PADDING_JUMPER.name(), "패딩점퍼");
        englishToKoreanTranslationMap.put(SubCategory.List.VEST.name(), "베스트");
        englishToKoreanTranslationMap.put(SubCategory.List.TSHIRT.name(), "티셔츠");
        englishToKoreanTranslationMap.put(SubCategory.List.HOODS_TSHIRT.name(), "후드티셔츠");
        englishToKoreanTranslationMap.put(SubCategory.List.SLEEVELESS.name(), "슬리브리스");
        englishToKoreanTranslationMap.put(SubCategory.List.SHIRT.name(), "셔츠");
        englishToKoreanTranslationMap.put(SubCategory.List.KNIT.name(), "니트");
        englishToKoreanTranslationMap.put(SubCategory.List.BLOUSE.name(), "블라우스");
        englishToKoreanTranslationMap.put(SubCategory.List.ONE_PIECE.name(), "원피스");
        englishToKoreanTranslationMap.put(SubCategory.List.DENIM_PANTS.name(), "데님팬츠");
        englishToKoreanTranslationMap.put(SubCategory.List.PANTS.name(), "팬츠");
        englishToKoreanTranslationMap.put(SubCategory.List.SHORTS.name(), "쇼츠");
        englishToKoreanTranslationMap.put(SubCategory.List.SKIRT.name(), "스커트");
    }

    public String englishToKorean(String english) {
        return englishToKoreanTranslationMap.get(english);
    }

    public String koreanToEnglish(String korean) {
        return koreanToEnglishTranslationMap.get(korean);
    }
}
