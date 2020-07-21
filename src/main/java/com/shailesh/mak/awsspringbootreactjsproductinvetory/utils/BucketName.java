package com.shailesh.mak.awsspringbootreactjsproductinvetory.utils;

public enum  BucketName {
    BUCKET_NAME("spring-boot-product-inventory");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
