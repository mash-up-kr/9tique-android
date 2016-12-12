package kr.co.mash_up.a9tique.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 판매상품을 나타내는 Model 클래스
 */
public class Product {

    private Long id;

    private String name;

    private String brandName;

    private String size;

    private int price;

    private String description;

    private MainCategory mainCategory;

    private SubCategory subCategory;

    private List<String> imageUrl = new ArrayList<>(4);

    private ProductStatus productStatus;

    private ZzimStatus zzimStatus;

    private SellerInfo sellerInfo;

    Date createdAt;

    Date updatedAt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


}
