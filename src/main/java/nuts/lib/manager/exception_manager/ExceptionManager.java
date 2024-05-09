package nuts.lib.manager.exception_manager;

import nuts.lib.manager.exception_manager.matchers.ValidExceptionMatcher;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.function.Supplier;

public class ExceptionManager {

    private final MessageSource messageSource;
    private final List<ExceptionMatcher> exceptionMatchers = supplierDefaultMatchers.get();

    public ExceptionManager(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public Object apply(Exception exception){

        for (ExceptionMatcher exceptionMatcher : exceptionMatchers) {
            if(exceptionMatcher.match(exception)){
                return exceptionMatcher.handle();
            };
        }

        throw new RuntimeException("not found support matcher");
    }
    static private final Supplier<List<ExceptionMatcher>> supplierDefaultMatchers = ()
            -> List.of(new ValidExceptionMatcher());

}
