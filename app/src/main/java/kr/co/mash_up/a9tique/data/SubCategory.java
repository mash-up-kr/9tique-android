package kr.co.mash_up.a9tique.data;

public class SubCategory {

    private String mName;

    public SubCategory(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public enum List {
        ALL,
        JACKET,  // 자켓
        COATS,  // 코트
        JUMPER,  // 점퍼
        HOODS_ZIPUP,  // 후드집업
        VEST,  // 베스트
        TSHIRT,  // 티셔츠
        HOODS_TSHIRT,  // 후드티셔츠
        SLEEVELESS,  // 슬리브리스
        SHIRT,  // 셔츠
        KNIT,  // 니트
        BLOUSE,  // 블라우스
        ONE_PIECE,  // 원피스
        DENIM_PANTS,  // 데님팬츠
        PANTS,  // 팬츠
        SHORTS,  // 쇼츠
        SKIRT  // 스커트
    }
}