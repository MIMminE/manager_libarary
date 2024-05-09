package nuts.lib.manager.exception_manager;

import java.util.function.Predicate;

public interface ExceptionMatcher {

    boolean match(Exception e);

    Object handle();
}
