package soccerstats.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import soccerstats.dal.user.UserRepository;

@Component
public class AuthService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        soccerstats.dal.user.User userDo = userRepository.findByLogin(login);

        if (userDo == null) {
            throw new UsernameNotFoundException("Login " + login + " not found");
        }

        return new User(userDo.getLogin(), userDo.getPassword(), AuthorityUtils.createAuthorityList("ROLE_EDIT_SOCCER_STATS"));
    }
}
