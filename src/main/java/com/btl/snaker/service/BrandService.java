package com.btl.snaker.service;

import com.btl.snaker.dto.BrandDTO;
import com.btl.snaker.entity.Brand;
import com.btl.snaker.repository.BrandRepository;
import com.btl.snaker.service.imp.BrandServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandService implements BrandServiceImp {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<BrandDTO> getAllBrands() {
        List<Brand> brands = brandRepository.findAll();
        List<BrandDTO> brandDTOs = new ArrayList<>();
        for (Brand brand : brands) {
            BrandDTO brandDTO = new BrandDTO();
            brandDTO.setId(brand.getId());
            brandDTO.setName(brand.getName());
            brandDTOs.add(brandDTO);
        }
        return brandDTOs;
    }

    @Override
    public BrandDTO getBrandById(int id) {
        Brand brand = brandRepository.findById(id);
        if(brand != null) {
            BrandDTO brandDTO = new BrandDTO();
            brandDTO.setId(brand.getId());
            brandDTO.setName(brand.getName());
            return brandDTO;
        }
        return null;
    }

    @Override
    public BrandDTO getBrandByName(String name) {
        Brand brand = brandRepository.findByName(name);
        if(brand != null) {
            BrandDTO brandDTO = new BrandDTO();
            brandDTO.setId(brand.getId());
            brandDTO.setName(brand.getName());
            return brandDTO;
        }
        return null;
    }
}
