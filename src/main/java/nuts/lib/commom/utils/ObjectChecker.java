package nuts.lib.commom.utils;

import java.util.Arrays;
import java.util.Objects;

public abstract class ObjectChecker {

    public static boolean areAnyNull(Object... values){
        return Arrays.stream(values).anyMatch(Objects::isNull);
    }
}
