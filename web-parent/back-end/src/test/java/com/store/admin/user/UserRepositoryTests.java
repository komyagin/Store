package com.store.admin.user;

import com.store.common.entity.Role;
import com.store.common.entity.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;
    @Test
    @Order(1)
    public void createUserTest() {
        Role admin = testEntityManager.find(Role.class, 1);
        User user = new User("me@dog.com", "P@$$word", "John", "Weak");
        user.addRole(admin);
        User savedUser = userRepository.save(user);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void createUserWithTwoRolesTest() {
        Role admin = testEntityManager.find(Role.class, 1);
        Role editor = testEntityManager.find(Role.class, 2);
        User user = new User("peter@grifin.com", "P@$$word", "Peter", "Grifin");
        user.addRole(admin);
        User savedUser = userRepository.save(user);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    @Order(3)
    public void listAllUsersTest() {
        Iterable<User> users = userRepository.findAll();
        users.forEach(System.out::println);
        System.out.println("FUCK 3");
    }

    @Test
    @Order(4)
    public void getUserByIdTest() {
        Optional<User> user = userRepository.findById(14);
        assertThat(user.orElseThrow().getId()).isEqualTo(14);
    }

    @Test
    @Order(5)
    public void updateUserTest() {
        User user = userRepository.findById(14).orElseThrow();
        user.setEnabled(true);
        userRepository.save(user);
        assertThat(user.getId()).isEqualTo(14);
    }

    @Test
    public void getUserByEmailTest() {
        String email = "poo@doo.to";
        User user = userRepository.getUserByEmail(email);

        assertThat(user).isNotNull();
    }

    @Test
    public void testCountByIdTest() {
        Integer id = 14;
        Long countById = userRepository.countById(100);
        assertThat(countById).isNotNull().isGreaterThan(0);
    }

    @Test
    public void disableUserByIdTest() {
        Integer id = 1;
        userRepository.updateEnableStatus(14, true);
        assertThat(userRepository.findById(14).get().isEnabled()).isTrue();
    }

    @Test
    public void paginationTest() {
        int pageNumber = 1;
        int pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = userRepository.findAll(pageable);

        List<User> userList = page.getContent();
        userList.forEach(System.out::println);
        assertThat(userList.size()).isEqualTo(pageSize);
    }

    @Test
    public void searchUserByKeyword() {
        String keyword = "bruce";
        int pageNumber = 0;
        int pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = userRepository.findAll(keyword, pageable);

        List<User> userList = page.getContent();
        userList.forEach(System.out::println);

        assertThat(userList.size()).isGreaterThan(0);
    }
}
