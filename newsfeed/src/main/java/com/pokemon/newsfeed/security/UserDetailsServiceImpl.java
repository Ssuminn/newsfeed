package com.pokemon.newsfeed.security;

import com.pokemon.newsfeed.entity.User;
import com.pokemon.newsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    // userDetailsService는 사용자 인증 처리하는 클래스 Spring Security에서 사용자 인증 정보를 로드하는데 사용
    private final UserRepository userRepository;
    // UserRepository 객체 생성 후 생성자를 통해 UserRepository 객체를 받아서 멤버 변수로 저장

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("NOT FOUND" + userId));

        return new UserDetailsImpl("testuser", "password", user);

        /*
        해당 메서드는 사용자 ID를 받아서 해당 ID를 가진 사용자를 데이터 베이스에서 조회하고,
        만약 사용자가 존재하지 않으면 예외를 던짐,
        조회된 사용자 정보를 UserDetailsImpl 객체로 변환하여 반환
         */
    }
}
