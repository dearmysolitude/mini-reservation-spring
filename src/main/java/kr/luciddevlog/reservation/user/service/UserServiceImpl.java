package kr.luciddevlog.reservation.user.service;

import kr.luciddevlog.reservation.user.dto.RegisterFormDto;
import kr.luciddevlog.reservation.user.entity.UserItem;
import kr.luciddevlog.reservation.user.entity.UserRole;
import kr.luciddevlog.reservation.user.exception.UserAlreadyExistsException;
import kr.luciddevlog.reservation.user.repository.UserItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    UserItemRepository userItemRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserItemRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.userItemRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private boolean registered(String userName) {
        return userItemRepository.findByUsername(userName) != null;
    }

    private boolean duplicatedPhoneNumber(String phoneNumber) {
        return userItemRepository.findByPhoneNumber(phoneNumber) != null;
    }

    public void register(RegisterFormDto form) {
        if(registered(form.getUsername())) {
            throw new UserAlreadyExistsException("이미 존재하는 ID입니다.");
        }
        if(duplicatedPhoneNumber(form.getPhoneNumber())) {
            throw new UserAlreadyExistsException("이미 존재하는 연락처입니다.");
        }

        String encodedPassword = passwordEncoder.encode(form.getPassword());

        UserItem register = UserItem.builder()
                .role(UserRole.ROLE_USER)
                .phoneNumber(form.getPhoneNumber())
                .username(form.getUsername())
                .name(form.getName())
                .password(encodedPassword)  // 직접 인코딩된 비밀번호 설정
                .build();

        userItemRepository.save(register);
    }

}
