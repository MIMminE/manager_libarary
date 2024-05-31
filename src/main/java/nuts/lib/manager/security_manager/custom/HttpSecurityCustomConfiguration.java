package nuts.lib.manager.security_manager.custom;

import nuts.lib.manager.security_manager.strategy.HttpSecurityStrategy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class HttpSecurityCustomConfiguration {

    public HttpSecurityCustomConfiguration() {
        init();
    }

    List<HttpSecurityStrategy> strategies = new ArrayList<>();

    public void apply(HttpSecurity httpSecurity) throws Exception {

        for (HttpSecurityStrategy strategyInfo : strategies) {
            strategyInfo.apply(httpSecurity);
        }
    }

    public void addStrategy(HttpSecurityStrategy... strategies) {
        this.strategies.addAll(Arrays.stream(strategies).toList());
    }


    private void init() {
        strategies.addAll(strategiesInit());
    }

    protected abstract List<HttpSecurityStrategy> strategiesInit();
}
