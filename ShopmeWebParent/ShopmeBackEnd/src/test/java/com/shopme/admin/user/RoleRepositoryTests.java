package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(value = false)
class RoleRepositoryTests {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void testCreateFirstRole() {
        Role roleAdmin = new Role("Admin", "manage everything");
        Role savedRole = roleRepository.save(roleAdmin);

        assertThat(savedRole.getId()).isGreaterThan(0);
    }
}
