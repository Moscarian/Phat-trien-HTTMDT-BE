package com.btl.snaker.service.imp;

import com.btl.snaker.dto.ProductDTO;
import com.btl.snaker.payload.ResponseData;
import com.btl.snaker.payload.request.CreateProductRequest;
import com.btl.snaker.payload.request.UpdateProductRequest;

import java.util.List;

public interface ProductServiceImp {
    ResponseData createProduct(CreateProductRequest createProductRequest);
    ResponseData updateProduct(UpdateProductRequest updateProductRequest);
    ResponseData deleteProduct(long id);
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(long id);
    List<ProductDTO> getProducts(Long categoryId, Long brandId, Long priceFrom, Long priceTo, String name);
    ResponseData getProductByName(String name);
}
