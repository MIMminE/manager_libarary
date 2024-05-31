package nuts.lib.manager.security_manager.custom;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nuts.lib.manager.security_manager.strategy.HttpSecurityStrategy;
import nuts.lib.manager.security_manager.strategy.FormLogin;
import nuts.lib.manager.security_manager.strategy.HttpBasic;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FormLoginBase extends HttpSecurityCustomConfiguration {

    static final public FormLoginBase INSTANCE = new FormLoginBase();

    @Override
    protected List<HttpSecurityStrategy> strategiesInit() {

        return List.of(
                FormLogin.enable,
                HttpBasic.enable
        );
    }
}
