package test.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import soccerstats.dal.user.User;
import soccerstats.dal.user.UserRepository;

@Component
public class TestAuthHelper {

    private final User user;

    @Autowired
    public TestAuthHelper(UserRepository userRepository) {
        userRepository.deleteAllInBatch();

        User user = new User();
        user.setLogin("testLogin");
        user.setPassword("testPassword");

        this.user = userRepository.save(user);
    }

    public User getUser() {
        return user;
    }

    public String getNotFoundLogin() {
        return "not.found";
    }
}
