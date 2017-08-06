package com.handicraft.core.service;

import com.handicraft.core.dto.Category;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 고승빈 on 2017-07-15.
 */
public interface CategoryService {

    List<Category> findCategoris();

    Category insertCategory(Category category);

    List<Category> updateCategoris(List<Category> categories);

    void deleteCategories();

    Category findCategoryByTid(long tid);

    Category updateCategorisByTid(Category category);

    void deleteCategoryByTid(long tid);
}
