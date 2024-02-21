package com.game.quizzzy.security.user;

import com.game.quizzzy.exception.UserNotFoundException;
import com.game.quizzzy.model.User;
import com.game.quizzzy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =
                userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        return ApiUserDetails.buildUserDetails(user);
    }
}
