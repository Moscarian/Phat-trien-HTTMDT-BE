package com.btl.snaker.service.imp;

import com.btl.snaker.dto.CategoryDTO;

import java.util.List;

public interface CategoryServiceImp {
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(long id);
}
