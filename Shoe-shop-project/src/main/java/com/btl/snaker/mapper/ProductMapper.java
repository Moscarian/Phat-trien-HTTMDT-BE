package com.btl.snaker.mapper;

import com.btl.snaker.dto.ProductDTO;
import com.btl.snaker.dto.ProductSizeDTO;
import com.btl.snaker.entity.Product;
import com.btl.snaker.entity.ProductSize;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {
    public static ProductDTO productToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setBrand(product.getBrand().getName());
        productDTO.setCategory(product.getCategory().getName());
        List<ProductSize> productSizes = product.getProductSizes();
        List<ProductSizeDTO> productSizeDTOS = new ArrayList<>();
        for(ProductSize productSize : productSizes) {
            ProductSizeDTO productSizeDTO = new ProductSizeDTO();
            productSizeDTO.setSize(productSize.getSize());
            productSizeDTO.setStock(productSize.getStock());
            productSizeDTOS.add(productSizeDTO);
        }
        productDTO.setSize(productSizeDTOS);
        productDTO.setGender(product.getGender());
        productDTO.setImage(product.getImage());
        productDTO.setPrice(product.getPrice());
        return productDTO;
    }
    public static List<ProductDTO> productListToProductDTOList(List<Product> productList) {
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : productList) {
            productDTOList.add(productToProductDTO(product));
        }
        return productDTOList;
    }
}
