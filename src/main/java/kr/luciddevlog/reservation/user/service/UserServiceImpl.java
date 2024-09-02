package kr.luciddevlog.reservation.user.service;

import kr.luciddevlog.reservation.user.dto.LoginForm;
import kr.luciddevlog.reservation.user.dto.MemberInfo;
import kr.luciddevlog.reservation.user.dto.RegisterForm;
import kr.luciddevlog.reservation.user.entity.UserItem;
import kr.luciddevlog.reservation.user.entity.UserRole;
import kr.luciddevlog.reservation.user.exception.InvalidCredentialsException;
import kr.luciddevlog.reservation.user.exception.UserAlreadyExistsException;
import kr.luciddevlog.reservation.user.exception.UserNotFoundException;
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
    private boolean duplicatedPhoneNumber(String userName) {
        return userItemRepository.findByPhoneNumber(userName) != null;
    }

    public void register(RegisterForm form) {
        if(registered(form.getUsername())) {
            throw new UserAlreadyExistsException("이미 존재하는 ID입니다.");
        }
        if(duplicatedPhoneNumber(form.getPhoneNumber())) {
            throw new UserAlreadyExistsException("이미 존재하는 연락처입니다.");
        }

//        UserItem register = UserItem.builder()
//                .role(UserRole.ROLE_USER)
//                .phoneNumber(form.getPhoneNumber())
//                .username(form.getUsername())
//                .name()
//                .build();
        UserItem member = form.toEntity();
        member.changeIntoEncodedPassword(passwordEncoder.encode(form.getPassword()));
        userItemRepository.save(member);
    }

    public MemberInfo makeMemberInfo(Long id) {
        UserItem member = userItemRepository.findById(id).orElse(null);
        if(member == null) {
            return null;
        }
        return new MemberInfo(member);
    }
}
