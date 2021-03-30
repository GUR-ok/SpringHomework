package homeworkSpringApp.security;

import homeworkSpringApp.model.User;
import homeworkSpringApp.security.jwt.JwtUser;
import homeworkSpringApp.security.jwt.JwtUserFactory;
import homeworkSpringApp.service.ListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final ListService service;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = service.getUserByName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("User {} loaded", userName);
        return jwtUser;
    }
}
