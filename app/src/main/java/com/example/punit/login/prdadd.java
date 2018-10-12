package com.example.punit.login;


public class prdadd {
    String ProductId;
    String ProductName;
    String ProductSize;
    String ProductColor;
    String ProductDetail;
    String valueOfProduct;
    String ProductHistory;



    public prdadd()
    {

    }

    public prdadd(String productId, String productName, String productSize, String productColor, String productDetail, String valueOfProduct, String productHistory)
    {
        this.ProductId = productId;
        this.ProductName = productName;
        this.ProductSize = productSize;
        this.ProductColor = productColor;
        this.ProductDetail = productDetail;
        this.valueOfProduct = valueOfProduct;
        this.ProductHistory = productHistory;
    }

    private String getProductId() {
        return ProductId;
    }

    private String getProductName() {
        return ProductName;
    }

    private String getProductSize() {
        return ProductSize;
    }

    private String getProductColor() {
        return ProductColor;
    }

    private String getProductDetail() {
        return ProductDetail;
    }

    private String getValueOfProduct() {
        return valueOfProduct;
    }

    private String getProductHistory() {
        return ProductHistory;
    }
}
