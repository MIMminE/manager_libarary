package nuts.lib.manager.security_manager.custom;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nuts.lib.manager.security_manager.strategy.HttpSecurityStrategy;
import nuts.lib.manager.security_manager.strategy.HttpBasic;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleBasicApi extends HttpSecurityCustomConfiguration {

    static final public SimpleBasicApi INSTANCE = new SimpleBasicApi();

    @Override
    protected List<HttpSecurityStrategy> strategiesInit() {
        return List.of(
                HttpBasic.enable
        );
    }
}
