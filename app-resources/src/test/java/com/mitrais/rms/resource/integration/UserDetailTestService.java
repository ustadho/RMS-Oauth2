package com.mitrais.rms.resource.integration;

import com.mitrais.rms.resource.domain.User;
import com.mitrais.rms.resource.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ActiveProfiles("test")
@Service(value = "userDetailTestServiceBean")
public class UserDetailTestService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String input) {
        Optional<User> user = null;

        if (input.contains("@"))
            user = userRepository.findByEmail(input);
        else
            user = userRepository.findByUsername(input);

        if (user == null)
            throw new BadCredentialsException("Bad credentials");

        new AccountStatusUserDetailsChecker().check(user.get());

        return user.get();
    }
}
