package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.math.BigDecimal;
import java.util.List;

// add the annotations to make this a REST controller

@RestController // add the annotation to make this controller the endpoint for the following
@RequestMapping("categories")  // http://localhost:8080/categories
@CrossOrigin // add annotation to allow cross site origin requests
public class CategoriesController {
    private CategoryDao categoryDao;
    private ProductDao productDao;

    // create an Autowired controller to inject the categoryDao and ProductDao
    @Autowired
    public CategoriesController(CategoryDao categoryDao, ProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }
    // add the appropriate annotation for a get action
    @GetMapping("")
    @PreAuthorize("permitAll()")

    public List<Category> getAll() {
        try {
            var category = categoryDao.getAllCategories();
            // find and return all categories
            return category;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
    // the url to return all products in category 1 would look like this
    @GetMapping("{categoryId}/products")
    @PreAuthorize("permitAll()")
      public List<Product> getProductsById(@PathVariable int categoryId) {

          try {
              var category = productDao.listByCategoryId(categoryId);

              if (category == null)
                  throw new ResponseStatusException(HttpStatus.NOT_FOUND);

              //Get products by categoryId
              return category;

          } catch (Exception ex) {
              throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
          }
      }
      // https://localhost:8080/categories/1/products
    @GetMapping("{id}")
    @PreAuthorize("permitAll()")
    public Category getById(@PathVariable int id) {
        try {
            var category = categoryDao.getById(id);

            if (category == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            return category;

        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
    @PostMapping()// add annotation to call this method for a POST action
    @PreAuthorize("hasRole('ROLE_ADMIN')")// add annotation to ensure that only an ADMIN can call this function
    public Category addCategory(@RequestBody Category category) {
        try {
            return categoryDao.create(category);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
    // add annotation to call this method for a PUT (update) action - the url path must include the categoryId
    @PutMapping("{id}")
    // add annotation to ensure that only an ADMIN can call this function
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateCategory(@PathVariable int id, @RequestBody Category category) {
        try {
            categoryDao.update(id, category);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }


    // add annotation to call this method for a DELETE action - the url path must include the categoryId
    @DeleteMapping("{id}")
    // add annotation to ensure that only an ADMIN can call this function
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteCategory(@PathVariable int id) {
        try {
            var category = categoryDao.getById(id);

            if (category == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            categoryDao.delete(id);

        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
}
