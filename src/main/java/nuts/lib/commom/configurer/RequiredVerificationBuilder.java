package nuts.lib.commom.configurer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public abstract class RequiredVerificationBuilder {

    protected abstract Supplier<HashMap<String, Object>> setVerification();

    protected void verify() {
        Set<Map.Entry<String, Object>> targetEntry = setVerification().get().entrySet();
        String exceptionMessage = "These instances are required during the build process :: ";

        List<String> exceptionKeyList
                = targetEntry.stream().filter(e -> e.getValue() == null).map(Map.Entry::getKey).toList();
        if (!exceptionKeyList.isEmpty()) {
            for (String key : exceptionKeyList) exceptionMessage += key + " ";
            throw new VerificationException(exceptionMessage);
        }
    }
}
