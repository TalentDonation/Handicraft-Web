package com.handicraft.api.controller;

import com.handicraft.core.dto.Category;
import com.handicraft.core.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 고승빈 on 2017-08-03.
 */

@RestController
@Api(value = "category" , description = "Category API")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    @ApiOperation(value = "" , notes = "Show categories")
    public List<Category> findCategories()
    {
        return categoryService.findCategoris();
    }

    @PostMapping("/categories")
    @ApiOperation(value = "" , notes = "Create catetory")
    public ResponseEntity insertCategories(@ModelAttribute Category category)
    {
        categoryService.insertCategory(category);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/categories")
    @ApiOperation(value = "" , notes = "Update categories")
    public ResponseEntity updateCategories(@ModelAttribute List<Category> categories)
    {
        categoryService.updateCategoris(categories);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/categories")
    @ApiOperation(value = "" , notes = "Delete categories")
    public ResponseEntity deleteCategories()
    {
        categoryService.deleteCategories();
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping("/categories/{tid}")
    @ApiOperation(value = "" , notes = "Show category")
    public Category findCategory(@PathVariable("tid") long tid)
    {
        return categoryService.findCategoryByTid(tid);
    }


    @PutMapping("/categories/{tid}")
    @ApiOperation(value = "" , notes = "Update category")
    public ResponseEntity updateCategories(@ModelAttribute Category category)
    {
        categoryService.updateCategorisByTid(category);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/categories/{tid}")
    @ApiOperation(value = "" , notes = "Delete category")
    public ResponseEntity deleteCategories(@PathVariable("tid") long tid)
    {
        categoryService.deleteCategoryByTid(tid);
        return new ResponseEntity(HttpStatus.OK);
    }

}
