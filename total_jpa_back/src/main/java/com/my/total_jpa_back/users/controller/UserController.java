package com.my.total_jpa_back.users.controller;

import com.my.total_jpa_back.common.dto.PageResponse;
import com.my.total_jpa_back.common.entity.Gender;
import com.my.total_jpa_back.common.exception.UserNotFoundException;
import com.my.total_jpa_back.users.dto.*;
import com.my.total_jpa_back.users.entity.Users;
import com.my.total_jpa_back.users.repository.UserRepository;
import com.my.total_jpa_back.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController : Restful한 API를 다룰 때 사용하는 어노테이션
// => @Controller, @ResponseBody가 합쳐져 있는 어노테이션
// => 위 두 개의 어노테이션을 따로 달아주면 @RestController 어노테이션 대체 가능
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    // Page -> DTO 로 return
    @GetMapping("/getPage")
    public PageResponse<UserResponse> findPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size ){

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );

        return userService.findPage(pageable);
    }

    // User 삭제 처리 API
    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable Long id){
        userService.delete(id);
        return "회원 삭제 완료";
    }

    // User 수정 API
    // 수정 대상은 Path Variable로 받고 값은 RequestBody 받아서 수정
    @PutMapping("/users/{id}") // 전체 수정할 때는 PUT, 부분 수정은 PATCH
    public UserResponse update(
            @PathVariable Long id,
            @RequestBody UserUpdateRequest request
            ) {
        return userService.update(id, request);
    }

    // 새로운 User 추가하기 API
    @PostMapping("/users")
    public UserResponse create(@RequestBody UserCreateRequest request) {
        return userService.create(request);
    }

    // 예외처리 테스트
    @GetMapping("/users/{id}")
    public Users findById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());
    }

    // RequestBody 테스트
    @PostMapping("/test")
    public HelloResponse requestBodyTest(@RequestBody HelloRequest request){
        // @RequestBody : json으로 보낸 데이터를 받는 어노테이션

        return HelloResponse.builder()
                .message("안녕하세요. " + request.getName())
                .age(request.getAge())
                .build();
    }

    // 전체 리스트를 요청
    @GetMapping("/users")
    public List<Users> findAll() {

        return userRepository.findAll();
    }

    // Path Variable(경로 변수)로 준 성별을 가지는 회원 리스트 요청
    @GetMapping("/gender/{gender}")
    public List<Users> findByGender(@PathVariable Gender gender) {
        return userRepository.findByGender(gender);
    }

    // Query Parameter(쿼리 파라미터)로 준 키워드를 포함하는 이름을 가진 회원 리스트 요청
    @GetMapping("/name")
    public List<Users> findByName(@RequestParam String keyword) {
        return userRepository.findByNameContaining(keyword);
    }

    // Query Parameter(쿼리 파라미터)로 준 색상을 좋아하는 회원 리스트 요청
    @GetMapping("/color")
    public List<Users> findByLikeColor(@RequestParam String color) {
        return userRepository.findByLikeColor(color);
    }

    // Query Parameter(쿼리 파라미터)로 준 색상과 성별을 각각 좋아하고 일치하는 회원 리스트 요청
    @GetMapping("/color-gender")
    public List<Users> findByLikeColorAndGender(
            @RequestParam("color") String color,
            @RequestParam("gender") Gender gender) {
        return userRepository.findByLikeColorAndGender(color, gender);
    }

    // Query Parameter(쿼리 파라미터)로 준 키워드를 포함하는 이메일을 가진 회원 리스트 요청
    @GetMapping("/email")
    public List<Users> findByEmail(@RequestParam String keyword) {
        return userRepository.findByEmailContaining(keyword);
    }

    // 이름 오름차순, 생성일 내림차순
    @GetMapping("/sort")
    public List<Users> findAllSort() {
        Sort sort = Sort.by("name").ascending()
                .and(
                        Sort.by("CreatedAt").descending()
                );

        return userRepository.findAll(sort);
    }

    @GetMapping("/page")
    public Page<Users> findAllPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );

        return userRepository.findAll(pageable);
    }

    @GetMapping("/slice")
    public Slice<Users> findAllSlice(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );

        return userRepository.findAllBy(pageable);
    }
}
