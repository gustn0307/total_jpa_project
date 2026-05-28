package com.my.total_jpa_back.users.repository;

import com.my.total_jpa_back.common.entity.Gender;
import com.my.total_jpa_back.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {

    // Query Method(쿼리 메서드) : 테이블 한 개에서 조회할 때 사용, join 불가
    // 1. 성별 조회
    List<Users> findByGender(Gender gender);

    // 2. 이름에 특정 문장을 포함하는 검색
    List<Users> findByNameContaining(String keyword);

    // 3. 좋아하는 색상 일치 자료를 검색
    List<Users> findByLikeColor(String color);

    // 4. 색상과 성별로 검색
    List<Users> findByLikeColorAndGender(String color, Gender gender);

}
