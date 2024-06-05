package nuts.lib.manager.security_manager.user;

import lombok.AccessLevel;
import lombok.Getter;
import nuts.lib.manager.verification_manager.annotation_verifier.Essential;

@Getter(AccessLevel.PACKAGE)
public class UserTableInfoConfigurer {
    @Essential
    private String tableName;

    @Essential
    private String userNameField;

    @Essential
    private String passWordField;

    @Essential
    private String enabledField;


    public UserTableInfoConfigurer tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public UserTableInfoConfigurer userNameField(String userNameField) {
        this.userNameField = userNameField;
        return this;
    }

    public UserTableInfoConfigurer passWordField(String passWordField) {
        this.passWordField = passWordField;
        return this;
    }

    public UserTableInfoConfigurer enabledField(String enabledField) {
        this.enabledField = enabledField;
        return this;
    }
}
