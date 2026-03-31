package com.btl.snaker.service;

import com.btl.snaker.dto.ProductDTO;
import com.btl.snaker.entity.Brand;
import com.btl.snaker.entity.Category;
import com.btl.snaker.entity.Product;
import com.btl.snaker.entity.ProductSize;
import com.btl.snaker.mapper.ProductMapper;
import com.btl.snaker.payload.ResponseData;
import com.btl.snaker.payload.request.AddSizeRequest;
import com.btl.snaker.payload.request.CreateProductRequest;
import com.btl.snaker.payload.request.UpdateProductRequest;
import com.btl.snaker.repository.BrandRepository;
import com.btl.snaker.repository.CategoryRepository;
import com.btl.snaker.repository.ProductRepository;
import com.btl.snaker.repository.ProductSizeRepository;
import com.btl.snaker.service.imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductService implements ProductServiceImp {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    public String formatName(String name) {
        String res="";
        String[] tmp = name.trim().split("\\s+");
        for(String it : tmp){
            res+=it.substring(0,1).toUpperCase()+it.substring(1).toLowerCase()+" ";
        }
        return res.trim();
    }

    @Override
    public ResponseData createProduct(CreateProductRequest createProductRequest) {
        ResponseData responseData = new ResponseData();
        try{
            Product product = new Product();
            Brand brand = brandRepository.findByName(formatName(createProductRequest.getBrandName()));
            if(brand == null){
                responseData.setSuccess(false);
                responseData.setDescription("Brand not found");
                return responseData;
            }
            Category category = categoryRepository.findByName(formatName(createProductRequest.getCategoryName()));
            if(category == null){
                responseData.setSuccess(false);
                responseData.setDescription("Category not found");
                return responseData;
            }
            product.setName(createProductRequest.getName());
            product.setImage(createProductRequest.getUrlImage());
            product.setDescription(createProductRequest.getDescription());
            product.setBrand(brand);
            product.setCategory(category);
            product.setPrice(createProductRequest.getPrice());
            product.setCreatedAt(new Date());
            List<ProductSize> productSizes = new ArrayList<>();
            for(AddSizeRequest addSizeRequest : createProductRequest.getAddSizeRequests()){
                ProductSize productSize = new ProductSize();
                productSize.setProduct(product);
                productSize.setSize(addSizeRequest.getSize());
                productSize.setStock(addSizeRequest.getQuantity());
                productSizes.add(productSize);
            }
            product.setProductSizes(productSizes);
            productRepository.save(product);
            responseData.setSuccess(true);
            responseData.setDescription("Success");
        } catch (Exception e) {
            responseData.setSuccess(false);
            responseData.setDescription(e.getMessage());
            e.printStackTrace();
        }
        return responseData;
    }

    @Override
    public ResponseData updateProduct(UpdateProductRequest updateProductRequest) {
        ResponseData responseData = new ResponseData();
        try{
            Product product = productRepository.findById(updateProductRequest.getProductId());
            if(product==null){
                responseData.setSuccess(false);
                responseData.setDescription("Product not found");
                return responseData;
            }
            Brand brand = brandRepository.findByName(formatName(updateProductRequest.getBrandName()));
            if(brand == null){
                responseData.setSuccess(false);
                responseData.setDescription("Brand not found");
                return responseData;
            }
            Category category = categoryRepository.findByName(formatName(updateProductRequest.getCategoryName()));
            if(category == null){
                responseData.setSuccess(false);
                responseData.setDescription("Category not found");
                return responseData;
            }
            product.setName(updateProductRequest.getName());
            product.setImage(updateProductRequest.getUrlImage());
            product.setDescription(updateProductRequest.getDescription());
            product.setBrand(brand);
            product.setCategory(category);
            product.setPrice(updateProductRequest.getPrice());
            product.setCreatedAt(new Date());
            AddSizeRequest sizeRequest = updateProductRequest.getAddSizeRequest();
            ProductSize productSize = productSizeRepository.findByProductAndSize(product, sizeRequest.getSize());
            if (productSize != null) {
                productSize.setStock(sizeRequest.getQuantity());
                productSizeRepository.save(productSize);
            } else {
                ProductSize newSize = new ProductSize();
                newSize.setProduct(product);
                newSize.setSize(sizeRequest.getSize());
                newSize.setStock(sizeRequest.getQuantity());
                productSizeRepository.save(newSize);
            }
            responseData.setSuccess(true);
            responseData.setDescription("Success");
            responseData.setSuccess(true);
            responseData.setDescription("Success");

        } catch (Exception e) {
            responseData.setSuccess(false);
            responseData.setDescription(e.getMessage());
            e.printStackTrace();
        }
        return responseData;
    }

    @Override
    public ResponseData deleteProduct(long id) {
        ResponseData responseData = new ResponseData();
        try {
            Product product = productRepository.findById(id);
            if(product == null){
                responseData.setSuccess(false);
                responseData.setDescription("Product Not Found");
                return responseData;
            }
            productRepository.delete(product);
            responseData.setSuccess(true);
            responseData.setDescription("Success");
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setSuccess(false);
            responseData.setDescription("Fail");
        }
        return responseData;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return ProductMapper.productListToProductDTOList(productRepository.findAll());
    }

    @Override
    public ProductDTO getProductById(long id) {
        Product product = productRepository.findById(id);
        return ProductMapper.productToProductDTO(product);
    }

    @Override
    public List<ProductDTO> getProducts(Long categoryId, Long brandId, Long priceFrom, Long priceTo, String name) {
        return ProductMapper.productListToProductDTOList(productRepository.findByCategoryIdAndBrandIdAndPriceAndName(categoryId, brandId, priceFrom, priceTo,name));
    }

    @Override
    public ResponseData getProductByName(String name) {
        ResponseData responseData = new ResponseData();
        List<Product> products = productRepository.findByNameContaining(name);
        List<ProductDTO> productDTOList = ProductMapper.productListToProductDTOList(products);
        responseData.setSuccess(true);
        responseData.setData(productDTOList);
        return responseData;
    }
}
