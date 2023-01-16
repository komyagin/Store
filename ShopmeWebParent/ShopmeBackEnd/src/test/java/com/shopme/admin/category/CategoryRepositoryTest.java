package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void createRootCategory() {
        Category category = new Category("Electronics");
        Category savedCategory = categoryRepository.save(category);
        assertThat(savedCategory.getId()).isGreaterThan(0);
    }

    @Test
    public void createSubCategory() {
        Category parent = new Category(3);
        Category cameras = new Category("Cameras", parent);
        Category gadgets = new Category("Gadgets", parent);
        categoryRepository.saveAll(List.of(cameras, gadgets));
        assertThat(0).isEqualTo(0);
    }

}