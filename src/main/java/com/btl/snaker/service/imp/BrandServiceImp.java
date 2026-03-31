package com.btl.snaker.service.imp;

import com.btl.snaker.dto.BrandDTO;

import java.util.List;

public interface BrandServiceImp {
    List<BrandDTO> getAllBrands();
    BrandDTO getBrandById(int id);
    BrandDTO getBrandByName(String name);
}
