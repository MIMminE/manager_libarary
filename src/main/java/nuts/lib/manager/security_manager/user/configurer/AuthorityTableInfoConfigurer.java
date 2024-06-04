package nuts.lib.manager.security_manager.user.configurer;

import lombok.Getter;
import nuts.lib.manager.verification_manager.annotation_verifier.Essential;

@Getter
public class AuthorityTableInfoConfigurer {
    @Essential
    private String authorityTableName;

    @Essential
    private String userNameField;

    @Essential
    private String authorityField;

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
