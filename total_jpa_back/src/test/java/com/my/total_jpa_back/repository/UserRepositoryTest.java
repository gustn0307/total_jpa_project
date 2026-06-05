package com.my.total_jpa_back.repository;

import com.my.total_jpa_back.common.entity.Gender;
import com.my.total_jpa_back.orders.entity.UserOrder;
import com.my.total_jpa_back.users.entity.Users;
import com.my.total_jpa_back.users.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

//    @Test
//    @Transactional
//    @DisplayName("JPQL로 N+1 해결")
//    void joinTest(){
//        List<Users> users = userRepository.findAllWithOrders();
//
//        for (Users user : users){
//            log.info("회원명: {}", user.getName());
//            for (UserOrder order : user.getOrders()){
//                log.info("주문번호 : {}, 제품명: {}",order.getId(), order.getProductName() );
//            }
//        }
//    }

//    @Test
//    @DisplayName("N+1 문제")
//    @Transactional
//    void nPlusOneTest() {
//        List<Users> users = userRepository.findAll();
//
//        // 회원수 500명 조회하면 500번의 주문정보 조회 쿼리가 추가됨 => N+1 : 500(주문정보) + 1(전체회원조회)
//        for (Users user : users){
//            log.info("회원명: {}", user.getName());
//            for (UserOrder order : user.getOrders()){
//                log.info("주문번호 : {}, 제품명: {}",order.getId(), order.getProductName() );
//            }
//        }
//    }

    // 회원정보 조회 후 주문 정보 조회
//    @Test
//    @DisplayName("회원정보 조회 후 주문 정보 조회")
//    @Transactional // 안 붙여주면 fetch Type이 LAZY일 때 UserOrder 정보 가져올 때 오류 발생
//    void findUserAndOrderTest() {
//        Users user = userRepository.findById(1L)
//                .orElseThrow();
//
//        // 회원 정보 조회
//        log.info("이름 : {}", user.getName());
//        log.info("이메일 : {}", user.getEmail());
//
//        // 주문 정보 조회
//        for (UserOrder order : user.getOrders()) {
//            log.info("주문 제품명 : {}", order.getProductName());
//            log.info("주문 제품 가격 : {}", order.getPrice());
//        }
//    }

    // Slice : 무한 스크롤 용으로 자료가 필요할 때 사용
    // 장점 : 가볍다(가지고 있는 정보가 적다) - 다음 페이지가 있는지만 가지고 있음
    //          전체 데이터 개수를 조회하는 count 쿼리가 실행되지 않아 속도가 빠름
    @Test
    @DisplayName("Slice 테스트")
    void sliceTest() {
        Pageable pageable = PageRequest.of(0, 20,
                Sort.by("createdAt").descending());

        // 페이징한 결과를 Slice에 저장
        Slice<Users> result = userRepository.findAll(pageable);

        List<Users> users = result.getContent();
        users.stream().forEach(x -> log.info("User = {}", x));

//        // 전체 페이지 수
//        log.info("전체 페이지 수 : {}", result.getTotalPages()); // Slice에서는 접근 불가
//        // 전체 자료 수
//        log.info("전체 자료 수  : {}", result.getTotalElements()); // Slice에서는 접근 불가
        // 현재 페이지
        log.info("현재 페이지 : {}", result.getNumber());
        // 다음 페이지 존재하는지
        log.info("다음 페이지 존재하는가? {}", result.hasNext());
        // 이전 페이지 존재하는지
        log.info("이전 페이지 존재하는가? {}", result.hasPrevious());

        result.forEach(x -> log.info("id : {}, name : {}, 가입일 : {}"
                , x.getId(), x.getName(), x.getCreatedAt()));
    }

    // 최근 가입한 회원 정보 중 10번째 페이지를 추출
    // 한 페이지당 30개씩 출력
    @Test
    @DisplayName("최근 가입한 회원 정보 중 10번째 페이지를 추출")
    void pageSortTest() {
        Sort sort = Sort.by("createdAt").descending();

        Pageable pageable = PageRequest.of(10, 30, sort);

        Page<Users> result = userRepository.findAll(pageable);

        // Page 객체는 내가 요청한 자료 + 기타 페이지 관련 정보
        // Page의 내용 중 리스트만 뽑아서 출력해보기
        List<Users> users = result.getContent();
        users.stream().forEach(x -> log.info("User = {}", x));

        // 전체 페이지 수
        log.info("전체 페이지 수 : {}", result.getTotalPages());
        // 전체 자료 수
        log.info("전체 자료 수  : {}", result.getTotalElements());
        // 현재 페이지
        log.info("현재 페이지 : {}", result.getNumber());
        // 다음 페이지 존재하는지
        log.info("다음 페이지 존재하는가? {}", result.hasNext());
        // 이전 페이지 존재하는지
        log.info("이전 페이지 존재하는가? {}", result.hasPrevious());

        result.forEach(x -> log.info("id : {}, name : {}, 가입일 : {}"
                , x.getId(), x.getName(), x.getCreatedAt()));
    }

    // 페이징 처리에 사용되는 클래스 : Pageable
    // 전체 회원 자료에서 10개 묶음으로 Paging 처리
    // PageRequest.of(페이지번호, 페이지당 개수)
    @Test
    @DisplayName("회원정보를 페이징으로 가져오기")
    void pagingTest() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Users> result = userRepository.findAll(pageable); // 페이징으로 넘겨주면 List가 아니라 Page로 받아야 한다.
        // Page 객체는 내가 요청한 자료 + 기타 페이지 관련 정보
        // Page의 내용 중 리스트만 뽑아서 출력해보기
        List<Users> users = result.getContent();
        users.stream().forEach(x -> log.info("All = {}", x));

        // 전체 페이지 수
        log.info("전체 페이지 수 : {}", result.getTotalPages());
        // 전체 자료 수
        log.info("전체 자료 수  : {}", result.getTotalElements());
        // 현재 페이지
        log.info("현재 페이지 : {}", result.getNumber());
        // 다음 페이지 존재하는지
        log.info("다음 페이지 존재하는가? {}", result.hasNext());
        // 이전 페이지 존재하는지
        log.info("이전 페이지 존재하는가? {}", result.hasPrevious());

        result.forEach(x -> log.info("id : {}, name : {}"
                , x.getId(), x.getName()));

    }


    @Test
    @DisplayName("회원 이름 오름차순 정렬")
    void orderByNameAscTest() {
        // Sort 클래스 이용
        Sort sort = Sort
                .by("name")
                .ascending();

        // 전체 검색할 때 Sort를 삽입해서 정렬되도록 처리
        List<Users> users = userRepository.findAll(sort);

        users.stream().limit(5).forEach(x -> log.info("name = {}", x.getName()));
//        for(Users user : users){
//            log.info("name = {}", user.getName());
//        }
    }

    // 최근 가입 회원 10명 출력
    @Test
    @DisplayName("최근 가입 회원 10명 출력")
    void orderbyCreatedAtDescTest() {
        Sort sort = Sort
                .by("createdAt")
                .descending();

        List<Users> users = userRepository.findAll(sort);

        users.stream()
                .limit(10)
                .forEach(x -> log.info("user_id = {}, name = {}, 가입일 = {}",
                        x.getId(), x.getName(), x.getCreatedAt()));
    }

    // 색상 오름차순, 같은 색상 자료는 이름 내림차순, 상위 100개 출력
    @Test
    @DisplayName("색상 오름차순, 같은 색상 자료는 이름 내림차순, 상위 100개 출력")
    void multiSortTest() {
        Sort sort = Sort
                .by("likeColor")
                .ascending()
                .and(
                        Sort
                                .by("name")
                                .descending()
                );

        List<Users> users = userRepository.findAll(sort);

        users.stream()
                .limit(100)
                .forEach(x -> log.info("color = {}, name = {}",
                        x.getLikeColor(), x.getName()));
    }

//    @Test
//    @DisplayName("회원 전체 조회")
//    void findAll() {
//        // given
//
//        // when
//        List<Users> users = userRepository.findAll();
//
//        // then
//        assertThat(users.size()).isEqualTo(500);
//    }

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