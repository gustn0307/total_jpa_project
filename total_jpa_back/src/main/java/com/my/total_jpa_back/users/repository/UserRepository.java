package com.my.total_jpa_back.users.repository;

import com.my.total_jpa_back.common.entity.Gender;
import com.my.total_jpa_back.users.entity.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {

    // JPQL : 자바 기반의 SQL

    // 테이블명이 아닌 자바의 엔티티 기준으로 쿼리문 작성
    // Alias를 꼭 적어줘야 한다.
    // fetch join : join 뒤에 fetch를 붙여줘야 한 번에 모든 정보를 가져온다.(가져오는 값이 List인 경우에만 붙인다.)
//    @Query("""
//    select distinct u
//        from Users u
//            join fetch u.orders
//    """)
//    List<Users> findAllWithOrders();


    // Query Method(쿼리 메서드) : 테이블 한 개에서 조회할 때 사용, join 불가
    // 1. 성별 조회
    List<Users> findByGender(Gender gender);

    // 2. 이름에 특정 문장을 포함하는 검색
    List<Users> findByNameContaining(String keyword);

    // 3. 좋아하는 색상 일치 자료를 검색
    List<Users> findByLikeColor(String color);

    // 4. 색상과 성별로 검색
    List<Users> findByLikeColorAndGender(String color, Gender gender);

    // 5. 이메일 중 특정 사이트 포함 이메일 계정 찾기
    List<Users> findByEmailContaining(String keyword);

    Slice<Users> findAllBy(Pageable pageable);
}
