package com.my.total_jpa_back.repository;

import com.my.total_jpa_back.entity.Gender;
import com.my.total_jpa_back.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원 전체 조회")
    void findAll() {
        // given

        // when
        List<Users> users = userRepository.findAll();

        // then
        assertThat(users.size()).isEqualTo(500);
    }

    @Test
    @DisplayName("성별 조회")
    void findByGender() {
        // given

        // when
        List<Users> users = userRepository.findByGender(Gender.Male);

        // then
        for (Users user : users)
            log.info("name = {}, gender = {} ", user.getName(), user.getGender());
    }

    @Test
    @DisplayName("이름에 kim을 포함하는 회원 조회")
    void findByNameContaining() {
        // given
        String keyword = "kim";
        // when
        List<Users> users = userRepository.findByNameContaining(keyword);

        // then
        for (Users user : users)
            log.info("name = {}, keyword = {} ", user.getName(), keyword);
    }

    @Test
    @DisplayName("좋아하는 색상으로 검색")
    void findByLikeColor() {
        // given
        String keyword = "orange";
        // when
        List<Users> users = userRepository.findByLikeColor(keyword);

        // then
        for (Users user : users)
            log.info("name = {}, likeColor = {} ", user.getName(), user.getLikeColor());
    }

    @Test
    @DisplayName("좋아하는 색상과 성별로 검색")
    void findByLikeColorAndGender() {
        // given
        String color = "orange";
        Gender gender = Gender.Female;
        // when
        List<Users> users = userRepository.findByLikeColorAndGender(color, gender);

        // then
        for (Users user : users)
            log.info("name = {}, likeColor = {}, gender = {} "
                    , user.getName(), user.getLikeColor(), user.getGender());

    }
}