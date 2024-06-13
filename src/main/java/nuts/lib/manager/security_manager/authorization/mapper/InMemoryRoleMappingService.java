package nuts.lib.manager.security_manager.authorization.mapper;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRoleMappingService implements RoleMappingService {

    static public String permitAll = "permitAll";
    static public String authenticated = "authenticated";
    static public String admin = "ROLE_ADMIN";
    static public String manager = "ROLE_MANAGER";
    static public String user = "ROLE_USER";

    private final Map<String, String> urlRoleMappings = new HashMap<>();

    @Override
    public Map<String, String> getRoleMappings() {
        return urlRoleMappings;
    }

    public void addUrlRole(String urlPatten, String role) {
        this.urlRoleMappings.put(urlPatten, role);
    }
}
