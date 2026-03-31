package com.btl.snaker.service;

import com.btl.snaker.dto.CategoryDTO;
import com.btl.snaker.entity.Category;
import com.btl.snaker.repository.CategoryRepository;
import com.btl.snaker.service.imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements CategoryServiceImp {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoriesDTO = new ArrayList<>();
        for(Category category : categories) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            categoriesDTO.add(categoryDTO);
        }
        return categoriesDTO;
    }

    @Override
    public CategoryDTO getCategoryById(long id) {
        Category category = categoryRepository.findById(id);
        if(category != null){
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            return categoryDTO;
        }
        return null;
    }
}
