package com.btl.snaker.payload.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class CreateProductRequest {
    private String name;
    private String urlImage;
    private String description;
    private String brandName;
    private String categoryName;
    private Long price;
    private List<AddSizeRequest> addSizeRequests;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public List<AddSizeRequest> getAddSizeRequests() {
        return addSizeRequests;
    }

    public void setAddSizeRequests(List<AddSizeRequest> addSizeRequests) {
        this.addSizeRequests = addSizeRequests;
    }
}
