package com.my.total_jpa_back.orders.repository;


import com.my.total_jpa_back.common.entity.OrderStatus;
import com.my.total_jpa_back.orders.entity.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {
    // 1. 전체 주문 조회
    // select * from user_order;

    // 2. 주문 상태(status)로 조회(COMPLETE 상태인 주문 정보들 조회)
    // select * from user_order where state = 'COMPLETE';
    List<UserOrder> findByStatus(OrderStatus status);

    // 3. 상품명 키워드 포함 검색(Dunk 포함 주문 조회)
    // select * from user_order where product_name like '%Dunk%';
    List<UserOrder> findByProductNameContaining(String keyword);

    // 4. 특정 가격 이상 조회(가격이 `300,000`원 이상인 주문을 조회)
    // >= : GreaterThanEqual
    // <= : LessThanEqual
    // select * from user_order where price >= 300000;
    List<UserOrder> findByPriceGreaterThanEqual(Integer price);

    // 5. 특정 회원 조회(user_id = 10)
    List<UserOrder> findByUserId(Long userId);

    // 6. 회원 + 주문 상태 조회(user_id= 10, COMPLETE)
    // select * from user_order where user_id = 10 and status = 'COMPLETE';
    List<UserOrder> findByUserIdAndStatus(Long userId, OrderStatus status);

    // 7. 가격 범위 조회(Between, 290000 ~ 300000)
    // select * from user_order where price between 290000 and 300000;
    List<UserOrder> findByPriceBetween(Integer start, Integer end);

    // 8. 가격 높은 순 조회(OrderBy)
    // findAll 뒤에 By를 붙여줘야 한다.
    // select * from user_order order by price desc;
    List<UserOrder> findAllByOrderByPriceDesc();

    // 9. 최신 주문 5개 조회(findTop5By......)  -- 최근 주문일의 내림차순 정렬..
    // `createdAt` 기준 최신 주문 5개를 조회하라.
    // select * from user_order order by created_at desc limit 5
    List<UserOrder> findTop5ByOrderByCreatedAtDesc();

    // 10. 상태 여러 개 조회(in, `READY` 또는 `SHIPPING` 상태인 주문을 조회)
    // in 구문은 리스트로 만들어서 전달한다.
    // select * from user_order where status in ('READY', 'SHIPPING')
    List<UserOrder> findByStatusIn(List<OrderStatus> statusList);

}
