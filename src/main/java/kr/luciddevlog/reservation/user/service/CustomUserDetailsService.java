package kr.luciddevlog.reservation.user.service;

import kr.luciddevlog.reservation.user.entity.CustomUserDetails;
import kr.luciddevlog.reservation.user.entity.UserItem;
import kr.luciddevlog.reservation.user.repository.UserItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    final UserItemRepository userItemRepository;
    @Autowired
    public CustomUserDetailsService(UserItemRepository userItemRepository) {
        this.userItemRepository = userItemRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserItem userItem = userItemRepository.findByUsername(username);
        if (userItem==null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new CustomUserDetails(userItem);
    }

}
