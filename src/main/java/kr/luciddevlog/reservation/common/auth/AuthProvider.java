package kr.luciddevlog.reservation.common.auth;

import kr.luciddevlog.reservation.user.entity.UserItem;
import kr.luciddevlog.reservation.user.repository.UserItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final UserItemRepository userItemRepository;

    @Autowired
    public AuthProvider(PasswordEncoder passwordEncoder, UserItemRepository userItemRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userItemRepository = userItemRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserItem userItem = userItemRepository.findByUsername(username);

		if(null == userItem || !passwordEncoder.matches(password, userItem.getPassword())) {
			return null;
		}

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        if(userItem.isAdmin()) {
            grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new MyAuthentication(username, password, grantedAuthorityList, userItem);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

class MyAuthentication extends UsernamePasswordAuthenticationToken {
    private static final long serialVersionUID = 1L;

    UserItem userItem;

    public MyAuthentication(String id, String password, List<GrantedAuthority> grantedAuthorityList, UserItem userItem) {
        super(id, password, grantedAuthorityList);
        this.userItem = userItem;
    }
}