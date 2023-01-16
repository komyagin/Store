package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import com.shopme.common.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer>, PagingAndSortingRepository<Category, Integer> {
}
