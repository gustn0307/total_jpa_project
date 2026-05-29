package com.my.total_jpa_back.orders.controller;

import com.my.total_jpa_back.common.entity.OrderStatus;
import com.my.total_jpa_back.orders.dto.OrderMultiSearchRequest;
import com.my.total_jpa_back.orders.dto.OrderMultiSearchResponse;
import com.my.total_jpa_back.orders.dto.OrderResponse;
import com.my.total_jpa_back.orders.service.UserOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserOrderController {
    private final UserOrderService userOrderService;

    @GetMapping("/status")
    public List<OrderResponse> findCompletedOrderResponse(@RequestParam OrderStatus status){
        return userOrderService.findResponseByStatus(status);
    }

    // 기술적으로 HTTP GET 요청 시 Request Body에 데이터를 담아 보낼 수는 있습니다.
    // 하지만 HTTP 표준 명세상 GET 메소드는 데이터를 조회하는 용도로 설계되었으며, Body를 포함하지 않는 것을 권장
    // 일부 프록시나 방화벽, 레거시 서버 환경에서는 Body가 포함된 GET 요청을 거부하거나 무시할 수 있으므로 주의
    @PostMapping("/several")
    public List<OrderMultiSearchResponse> findResponseBySeveral(
            @RequestBody OrderMultiSearchRequest request
            ){
        return userOrderService.multiSearch(request);
    }

    @GetMapping("/users/{id}/orders")
    public List<OrderResponse> findResponseByUserId(@PathVariable Long id){
        return userOrderService.findResponseByUserId(id);
    }
}
