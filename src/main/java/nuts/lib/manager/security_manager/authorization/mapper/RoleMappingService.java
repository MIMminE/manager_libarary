package nuts.lib.manager.security_manager.authorization.mapper;

import java.util.Map;

/**
 * Interface for providing Spring Security privilege information.
 * <p>
 * The basic implementations are {@link InMemoryRoleMappingService}, {@link JdbcBasedRoleMappingService}.
 *
 * @created 2024. 06. 01
 */
public interface RoleMappingService {

    Map<String, String> getRoleMappings();

}
