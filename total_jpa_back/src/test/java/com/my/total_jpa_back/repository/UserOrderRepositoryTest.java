package com.my.total_jpa_back.repository;

import com.my.total_jpa_back.common.entity.OrderStatus;
import com.my.total_jpa_back.orders.dto.OrderResponse;
import com.my.total_jpa_back.orders.entity.UserOrder;
import com.my.total_jpa_back.orders.repository.UserOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Slf4j
class UserOrderRepositoryTest {

    @Autowired
    UserOrderRepository orderRepository;

    //
//    @Test
//    @Transactional
//    @DisplayName("DTO로 결과 받기")
//    void dtoResultTest() {
//        List<OrderResponse> result = orderRepository.findOrderResponse();
//
//        result.stream()
//                .limit(100)
//                .forEach(x ->
//                        log.info("주문번호 : {}, 제품명: {}, 고객명 : {}",
//                                x.getOrderId(), x.getProductName(), x.getUserName()));
//    }

    // Many To One Test
//    @Test
//    @DisplayName("주문 조회 / 조회 후 회원 정보 확인")
//    @Transactional // 안 붙여주면 fetch Type이 LAZY일 때 User 정보 가져올 때 오류 발생
//    void findOrderAndUserTest() {
//        UserOrder order = orderRepository.findById(1L)
//                .orElseThrow();
//
//        // 주문 조회
////        log.info(" : {}", order.);
//        log.info("order id : {}", order.getId());
//        log.info("제품명 : {}", order.getProductName());
//        log.info("가격 : {}", order.getPrice());
//        log.info("배송상태 : {}", order.getStatus());
//
//
//        // 회원 정보 확인
//        log.info("user_id : {}", order.getUser().getId());
//        log.info("고객명 : {}", order.getUser().getName());
//        log.info("이메일 : {}", order.getUser().getEmail());
//        log.info("성별 : {}", order.getUser().getGender());
//        log.info("좋아하는 색 : {}", order.getUser().getLikeColor());
//    }


    // 주문 상태에 따른 오름차순 정렬 후 제품명에 대해 내림차순 정렬 후, 주문일 내림차순 1000개 출력
    @Test
    @DisplayName("주문 상태에 따른 오름차순 정렬 후 제품명에 대해 내림차순 정렬 후, 주문일 내림차순 100개 출력")
    void multiSortTest() {
        Sort sort = Sort.by("status").ascending()
                .and(
                        Sort.by("productName").descending()
                                .and(
                                        Sort.by("createdAt").descending()
                                )
                );

        List<UserOrder> userOrders = orderRepository.findAll(sort);

        userOrders.stream()
                .limit(1000)
                .forEach(
                        x -> log.info("주문 상태 : {}, 제품명 : {}, 주문일 : {} "
                                , x.getStatus(), x.getProductName(), x.getCreatedAt())
                );
    }


    @Test
    @DisplayName("전체 주문 조회")
    void findAll() {
        List<UserOrder> userOrders = orderRepository.findAll();
        log.info("order count = {}", userOrders.size());

        assertThat(userOrders.size()).isEqualTo(5000);
    }

    @Test
    @DisplayName("주문 상태로 조회")
    void findByStatus() {
        OrderStatus status = OrderStatus.COMPLETE;

        List<UserOrder> userOrders = orderRepository.findByStatus(status);

        log.info("order count = {}", userOrders.size());

        for (UserOrder order : userOrders)
            log.info("product_name = {}, status = {}", order.getProductName(), order.getStatus());
    }

    @Test
    @DisplayName("상품 이름 중 일부를 키워드로 검색")
    void findByProductNameContaining() {
        String keyword = "Dunk";

        List<UserOrder> userOrders = orderRepository.findByProductNameContaining(keyword);

        log.info("order count = {}", userOrders.size());

        for (UserOrder order : userOrders)
            log.info("product_name = {}, keyword = {}", order.getProductName(), keyword);
    }

    @Test
    @DisplayName("특정 가격보다 가격이 비싼 주문 정보 조회")
    void findByPriceGreaterThanEqual() {
        Integer price = 300000;

        List<UserOrder> userOrders = orderRepository.findByPriceGreaterThanEqual(price);

        log.info("order count = {}", userOrders.size());

        for (UserOrder order : userOrders)
            log.info("product_name = {}, price = {}, Reference price = {}", order.getProductName(), order.getPrice(), price);
    }

//    @Test
//    @DisplayName("user_id로 주문 정보 조회")
//    void findByUserId() {
//        Long userId = 10L;
//
//        List<UserOrder> userOrders = orderRepository.findByUserId(userId);
//
//        log.info("order count = {}", userOrders.size());
//
//        for (UserOrder order : userOrders)
//            log.info("product_name = {}, userId = {}", order.getProductName(), order.getUserId());
//    }

//    @Test
//    @DisplayName("user_id와 주문 상태로 조회")
//    void findByUserIdAndStatus() {
//        Long userId = 10L;
//        OrderStatus status = OrderStatus.COMPLETE;
//
//        List<UserOrder> userOrders = orderRepository.findByUserIdAndStatus(userId, status);
//
//        log.info("order count = {}", userOrders.size());
//
//        for (UserOrder order : userOrders)
//            log.info("product_name = {}, userId = {}, status = {}", order.getProductName(), order.getUserId(), order.getStatus());
//    }

    @Test
    @DisplayName("가격 범위 조회")
    void findByPriceBetween() {
        Integer startPrice = 290000;
        Integer endPrice = 300000;

        List<UserOrder> userOrders = orderRepository.findByPriceBetween(startPrice, endPrice);

        log.info("order count = {}", userOrders.size());

        for (UserOrder order : userOrders)
            log.info("product_name = {}, price = {}", order.getProductName(), order.getPrice());
    }

    @Test
    @DisplayName("가격 높은 순 조회")
    void findAllByOrderByPriceDesc() {
        List<UserOrder> userOrders = orderRepository.findAllByOrderByPriceDesc();

        log.info("order count = {}", userOrders.size());

        for (UserOrder order : userOrders)
            log.info("product_name = {}, price = {}", order.getProductName(), order.getPrice());
    }

    @Test
    @DisplayName("최신 주문 5개 조회")
    void findTop5ByOrderByCreatedAtDesc() {
        List<UserOrder> userOrders = orderRepository.findTop5ByOrderByCreatedAtDesc();

        log.info("order count = {}", userOrders.size());

        for (UserOrder order : userOrders)
            log.info("product_name = {}, created_at = {}", order.getProductName(), order.getCreatedAt());

    }

    @Test
    @DisplayName("상태 여러 개 조회(in)")
    void findByStatusIn() {
        List<OrderStatus> statusList = new ArrayList<>(
                List.of(OrderStatus.READY, OrderStatus.SHIPPING)
        );
//        statusList.add(OrderStatus.READY);
//        statusList.add(OrderStatus.SHIPPING);

        List<UserOrder> userOrders = orderRepository.findByStatusIn(statusList);

        log.info("order count = {}", userOrders.size());

        for (UserOrder order : userOrders)
            log.info("product_name = {}, status = {}", order.getProductName(), order.getStatus());
    }
}