package nuts.lib.manager.security_manager.user;

import lombok.AccessLevel;
import lombok.Getter;
import nuts.lib.manager.verification_manager.annotation_verifier.Essential;

@Getter(AccessLevel.PACKAGE)
public class AuthorityTableInfoConfigurer {
    @Essential
    String authorityTableName;

    @Essential
    String userNameField;

    @Essential
    String authorityField;

    public AuthorityTableInfoConfigurer authorityTableName(String authorityTableName) {
        this.authorityTableName = authorityTableName;
        return this;
    }

    public AuthorityTableInfoConfigurer userNameField(String userNameField) {
        this.userNameField = userNameField;
        return this;
    }

    public AuthorityTableInfoConfigurer authorityField(String authorityField) {
        this.authorityField = authorityField;
        return this;
    }
}
