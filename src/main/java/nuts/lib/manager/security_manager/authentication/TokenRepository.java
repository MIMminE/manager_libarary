package nuts.lib.manager.security_manager.authentication;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface TokenRepository {

    void saveToken(String token, List<? extends GrantedAuthority> authorities);

    List<? extends GrantedAuthority> getAuthorities(String token);

}
