package nuts.lib.manager.exception_manager;

public interface ExceptionMatcher {

    boolean match(Exception e);

    Object handle();
}
