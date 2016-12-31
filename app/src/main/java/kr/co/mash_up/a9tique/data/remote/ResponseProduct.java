package kr.co.mash_up.a9tique.data.remote;

import java.util.List;

import kr.co.mash_up.a9tique.data.Product;


public class ResponseProduct {

    private List<Product> mProducts;

    private int currentPageNo;

    private int pageTotal;   // 전체 페이지 수. 이거보다 -1해서 요청해야 한다.

    public ResponseProduct(List<Product> products, int currentPageNo, int pageTotal) {
        mProducts = products;
        this.currentPageNo = currentPageNo;
        this.pageTotal = pageTotal;
    }

    public List<Product> getProducts() {
        return mProducts;
    }

    public void setProducts(List<Product> products) {
        mProducts = products;
    }

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }
}
