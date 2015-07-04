package soccerstats.auth;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import soccerstats.Application;
import soccerstats.dal.user.User;
import soccerstats.dal.user.UserRepository;
import test.common.TestAuthHelper;

import java.util.*;

@SpringApplicationConfiguration(classes = {Application.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    private AuthService authService;

    private TestAuthHelper authHelper;

    private User userDo;

    @Before
    public void setUp() throws Exception {
        authHelper = new TestAuthHelper(userRepository);
        userDo = authHelper.getUser();

        authService = new AuthService(userRepository);
    }

    @Test
    public void loadUserByUsername_Success() throws Exception {
        UserDetails actualUser = authService.loadUserByUsername(userDo.getLogin());

        Comparator<GrantedAuthority> comparator = (g1, g2) -> {
            return g1.getAuthority().compareTo(g2.getAuthority());
        };

        SortedSet<GrantedAuthority> expectedAuthorities = new TreeSet<>(comparator);
        expectedAuthorities.addAll(AuthorityUtils.createAuthorityList("ROLE_EDIT_SOCCER_STATS"));

        SortedSet<GrantedAuthority> actualAuthorities = new TreeSet<>(comparator);
        actualAuthorities.addAll(actualUser.getAuthorities());

        Assert.assertEquals(userDo.getLogin(), actualUser.getUsername());
        Assert.assertEquals(userDo.getPassword(), actualUser.getPassword());
        Assert.assertEquals(expectedAuthorities, actualAuthorities);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_NotFound() throws Exception {
        String notFoundUser = authHelper.getNotFoundLogin();
        UserDetails notFoundDetails = authService.loadUserByUsername(notFoundUser);
    }
}
