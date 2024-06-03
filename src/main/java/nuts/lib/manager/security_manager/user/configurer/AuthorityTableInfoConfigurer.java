package nuts.lib.manager.security_manager.user.configurer;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class AuthorityTableInfoConfigurer {
    private String authorityTableName;
    private String userNameField;
    private String authorityField;
}
