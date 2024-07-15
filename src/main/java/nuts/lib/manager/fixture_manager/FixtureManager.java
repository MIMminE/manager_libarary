package nuts.lib.manager.fixture_manager;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BeanArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.jqwik.JavaTypeArbitraryGenerator;
import com.navercorp.fixturemonkey.api.jqwik.JqwikPlugin;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.arbitraries.StringArbitrary;

import java.util.function.Supplier;

/**
 * A manager used to create test class fixtures that are primarily used for testing purposes.
 * <p>
 * We need a class-specific builder because we create a fixture by referencing the builders in a given class.
 * <p>
 * Apply the Validation annotation in the field.
 * <p>
 * <pre>
 * {@code
 * FixtureMonkey fixtureMonkey = FixtureManager.supplierDefault.get();
 * RequestUser requestCreateGroup = fixtureMonkey.giveMeOne(RequestUser.class);
 * }
 * </pre>
 * <p>
 * This class can be used separately, but the purpose is to inherit and use {@link FixtureGenerateSupport} for the test class.
 *
 * @author nuts
 * @since 2024. 05. 14
 */
public abstract class FixtureManager {

    static public Supplier<FixtureMonkey> supplierDefault = () -> FixtureMonkey.builder()
            .objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
            .defaultNotNull(true).nullableElement(false)
            .plugin(new JakartaValidationPlugin())
            .plugin(new JqwikPlugin()
                    .javaTypeArbitraryGenerator(new JavaTypeArbitraryGenerator() {
                        @Override
                        public StringArbitrary strings() {
                            return Arbitraries.strings().alpha();
                        }
                    }))
            .build();

    static public Supplier<FixtureMonkey> supplierSetterObjectIntrospect = () -> FixtureMonkey.builder()
            .objectIntrospector(BeanArbitraryIntrospector.INSTANCE)
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
