package com.handicraft.core.service;

import com.handicraft.core.dto.Category;
import com.handicraft.core.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 고승빈 on 2017-07-15.
 */
@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> findCategoris() {
        return categoryRepository.findAll();
    }

    @Override
    public Category insertCategory(Category category) {

        if(categoryRepository.exists(category.getTid()))
        {
            category.setTid(categoryRepository.findTopByOrderByTidDesc().getTid() + 1);
        }

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> updateCategoris(List<Category> categories) {
        return categoryRepository.save(categories);
    }

    @Override
    public void deleteCategories() {

        categoryRepository.deleteAll();
    }

    @Override
    public Category findCategoryByTid(long tid) {
        return categoryRepository.findOne(tid);
    }

    @Override
    public Category updateCategorisByTid(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategoryByTid(long tid) {
        categoryRepository.delete(tid);
    }
}
