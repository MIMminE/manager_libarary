package nuts.lib.manager.exception_manager.matchers;

import nuts.lib.manager.exception_manager.ExceptionMatcher;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class ValidExceptionMatcher implements ExceptionMatcher {

    @Override
    public boolean match(Exception e) {
        return e instanceof MethodArgumentNotValidException;
    }

    @Override
    public Object handle() {
        System.out.println("tt");
        return null;
    }
}
