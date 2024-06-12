package nuts.lib.manager.security_manager.user.jdbc.user_manager;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class JdbcUserDetails implements UserDetails {

    private final String userName;
    private String password;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;
    private final List<GrantedAuthority> authorities;

    public static List<? extends GrantedAuthority> createGrantedAuthority(String... authorityName) {
        return Arrays.stream(authorityName).map(SimpleGrantedAuthority::new).toList();
    }

    public JdbcUserDetails(String userName, List<GrantedAuthority> authorities) {
        this(userName, true, true, true, true, authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "JdbcUserDetails{" +
                "userName='" + userName + '\'' +
                ", password='" + "[protected]" + '\'' +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", enabled=" + enabled +
                ", authorities=" + authorities +
                '}';
    }

    private JdbcUserDetails(String userName, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled, List<GrantedAuthority> authorities) {
        this.userName = userName;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.authorities = authorities;
    }
}
