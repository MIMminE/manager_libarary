package nuts.lib.manager.security_manager;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import java.util.HashMap;
import java.util.Map;

public class SecurityCustomConfiguration {


    private final Map<Class<? extends SecurityStrategy>, SecurityStrategy> strategyMap = new HashMap<>();


    boolean csrf = false;
    boolean basic = false;

    public void OnCsrf(){
        this.csrf = true;
    }

    public void onBasic(){
        this.basic = true;
    }



    public HttpSecurity apply(HttpSecurity httpSecurity){

        return httpSecurity;
    }
}
