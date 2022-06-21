package com.cvc.teste.hoteis.mapper;


import org.springframework.stereotype.Component;

import com.cvc.teste.hoteis.dto.CategoryDTO;

@Component
public class CategoryMapper {
    
    public CategoryDTO categoryToCategoryDTO(String category) {
        CategoryDTO categoryDTO = CategoryDTO.builder()
        .name(category).build();

        return categoryDTO;
    }
}
