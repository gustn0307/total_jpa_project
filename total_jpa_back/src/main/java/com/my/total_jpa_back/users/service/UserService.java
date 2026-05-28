package com.my.total_jpa_back.users.service;

import com.my.total_jpa_back.common.exception.UserNotFoundException;
import com.my.total_jpa_back.users.dto.UserCreateRequest;
import com.my.total_jpa_back.users.dto.UserResponse;
import com.my.total_jpa_back.users.dto.UserUpdateRequest;
import com.my.total_jpa_back.users.entity.Users;
import com.my.total_jpa_back.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse create(UserCreateRequest request) {
        Users user = new Users();
        user.setName(request.getName());
        user.setGender(request.getGender());
        user.setEmail(request.getEmail());
        user.setLikeColor(request.getLikeColor());

        // Repository에 저장요청
        // Repo의 save() 메서드는 기본적으로 저장하고 난 다음 엔티티를 반환함
        Users savedUser = userRepository.save(user);

        // 엔티티 -> DTO로 변환해서 리턴
        return UserResponse.from(savedUser);
    }

    @Transactional
    public UserResponse update(Long id, UserUpdateRequest request) {
        // 수정할 User DB에서 찾기, 없으면 직접 정의한 에러 발생
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException() );

        // 수정
        user.setName(request.getName());
        user.setGender(request.getGender());
        user.setEmail(request.getEmail());
        user.setLikeColor(request.getLikeColor());

        // 엔티티 -> DTO로 변환해서 리턴
        // 저장하지 않고 @Transactional 붙였기 때문에
        // 영속성 컨텍스트에서 Dirty Checking를 통해 바뀐 것을 DB에 저장해줌
        // Dirty Checking : 영속 상태의 Entity를 수정하면 트랜잭션 커밋 시점에 JPA가 자동으로 UPDATE 쿼리를 실행한다.
        // return 해주면 트랜잭션이 commit되어 영속성 컨텍스트가 알아서 수정된 값으로 저장 처리 해준다.
        return UserResponse.from(user);
    }

    @Transactional
    public void delete(Long id) {
        // 해당하는 ID가 존재하는지 확인하고 없으면 Exception 처리
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());

        userRepository.delete(user);
    }
}
