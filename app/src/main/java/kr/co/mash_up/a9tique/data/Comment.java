package kr.co.mash_up.a9tique.data;

/**
 * Created by seokjunjeong on 2017. 7. 8..
 */

public class Comment {
    public String imgUrl;
    public String name;
    public String comment;
    public long create_at;
    public long update_at;


    public Comment(String imgUrl, String name, String comment, long create_at, long update_at) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.comment = comment;
        this.create_at = create_at;
        this.update_at = update_at;
    }
}
