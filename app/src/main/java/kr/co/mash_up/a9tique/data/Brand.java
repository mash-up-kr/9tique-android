package kr.co.mash_up.a9tique.data;

/**
 * Created by seokjunjeong on 2017. 7. 23..
 */

public class Brand {
    public long id;
    public String headerTitle;
    public String nameKo;
    public String nameEn;

    public Brand(long id, String headerTitle, String nameKo, String nameEn) {
        this.id = id;
        this.headerTitle = headerTitle;
        this.nameKo = nameKo;
        this.nameEn = nameEn;
    }
}
