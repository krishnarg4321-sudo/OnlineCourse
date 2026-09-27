package com.elearning.portal.security;

import com.elearning.portal.entity.User;
import com.elearning.portal.exception.UnauthorizedException;
import com.elearning.portal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserPrincipal loadUserByUsername(String username) {
        User user = userRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));
        return UserPrincipal.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPasswordHash())
                .role(user.getRole())
                .accountStatus(user.getAccountStatus())
                .build();
    }
}
