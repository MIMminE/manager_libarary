package nuts.lib.manager.fixture_manager;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.jqwik.JavaTypeArbitraryGenerator;
import com.navercorp.fixturemonkey.api.jqwik.JqwikPlugin;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.arbitraries.StringArbitrary;

import java.util.function.Supplier;

public abstract class FixtureManager {

    static public Supplier<FixtureMonkey> supplierDefault = () -> FixtureMonkey.builder()
        .objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
        .defaultNotNull(true).nullableElement(true).nullableElement(true)
        .plugin(new JakartaValidationPlugin())
        .plugin(new JqwikPlugin()
                .javaTypeArbitraryGenerator(new JavaTypeArbitraryGenerator() {
                    @Override
                    public StringArbitrary strings() {
                        return Arbitraries.strings().alpha();
                    }
                }))
        .build();
}
