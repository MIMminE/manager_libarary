package nuts.lib.manager.security_manager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import static org.junit.jupiter.api.Assertions.*;

class SecurityManagerTest {

    HttpSecurity security;

    @Test
    void test() {

        SecurityManager securityManager = new SecurityManager(security);


        securityManager
                .
    }

}