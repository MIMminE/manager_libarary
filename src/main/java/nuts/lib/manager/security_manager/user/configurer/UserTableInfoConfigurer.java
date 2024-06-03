package nuts.lib.manager.security_manager.user.configurer;

import lombok.Getter;

@Getter
public class UserTableInfoConfigurer {
    private String tableName;
    private String userNameField;
    private String passWordField;
    private String enabledField;
}
