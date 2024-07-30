package nuts.lib.manager.fixture_manager;

import com.navercorp.fixturemonkey.FixtureMonkey;
import lombok.Getter;
import lombok.ToString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static nuts.lib.manager.fixture_manager.FixtureMonkeySupplier.supplierFieldReflection;

class FixtureManagerTest {


    @DisplayName("[ Fixture Manager ] : Fixture creation succeeded ")
    @Test
    void supplierFieldReflectionFixtureTestSuccess() {
        FixtureMonkey fixtureMonkey = supplierFieldReflection.get();

        List<FixtureTargetClass> fixtureTargetClasses = fixtureMonkey.giveMe(FixtureTargetClass.class, 10);
        for (FixtureTargetClass fixtureTargetClass : fixtureTargetClasses) {
            Assertions.assertThat(fixtureTargetClass.getInteger()).isBetween(0, 10000);
            Assertions.assertThat(fixtureTargetClass.getString()).hasSizeBetween(3, 15);
            Assertions.assertThat(fixtureTargetClass.getStringList()).allMatch(e -> e.length() >= 3 && e.length() <= 15);
        }
    }

    @Getter
    @ToString
    static class FixtureTargetClass {
        int integer;
        String string;
        List<String> stringList;
        Map<String, String> stringObjectMap;
        List<FixtureInnerTargetClass> innerTargetClasses;
        Map<String, FixtureInnerTargetClass> innerTargetClassMap;
    }

    @Getter
    @ToString
    static class FixtureInnerTargetClass {
        int innerInteger;
        String innerString;
    }

}