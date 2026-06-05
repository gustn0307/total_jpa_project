package com.my.total_jpa_back.orders.repository;

import com.my.total_jpa_back.orders.dto.OrderSearchCondition;
import com.my.total_jpa_back.orders.entity.QUserOrder;
import com.my.total_jpa_back.orders.entity.UserOrder;
import com.my.total_jpa_back.users.entity.QUsers;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QueryDslOrderRepository {
    private final JPAQueryFactory queryFactory;

    // 조건을 검색하는 메서드를 생성
    // QueryDSL을 사용하는 이유: 복잡한 조건을 가진 쿼리를 동적으로 쿼리 생성 가능
    public List<UserOrder> search(OrderSearchCondition condition) {
        QUserOrder userOrder = QUserOrder.userOrder;
        QUsers users = QUsers.users;

        // 동적 조건을 만들 때 사용
        BooleanBuilder builder = new BooleanBuilder();

        // 주문 상태 체크용
        if (condition.getStatus() != null) {
            // 주문상태가 널이 아니면 sql where 구문 생성
            builder.and(
                    userOrder.status.eq(
                            condition.getStatus()
                    )
            );
        }

        // 최소 금액
        if (condition.getMinPrice() != null) {
            builder.and(
                    userOrder.price.goe(
                            condition.getMinPrice()
                    )
            );
        }

        // 회원 이름
        if (! ObjectUtils.isEmpty(condition.getUserName())) {
            builder.and(
                    users.name.contains(condition.getUserName())
            );
        }

        if (condition.getMaxPrice() != null){
            builder.and(
                    userOrder.price.loe(
                            condition.getMaxPrice()
                    )
            );
        }

        // 쿼리를 동적으로 생성해서 리턴
        return queryFactory
                .selectFrom(userOrder)
                .join(userOrder.user, users)
                .fetchJoin() // 두 테이블 조인해서 한 번에 가져오기
                .where(builder)
                .fetch()
                ;
    }

}
