package nuts.lib.manager.fixture_manager;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BeanArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.jqwik.JavaTypeArbitraryGenerator;
import com.navercorp.fixturemonkey.api.jqwik.JqwikPlugin;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.arbitraries.IntegerArbitrary;
import net.jqwik.api.arbitraries.LongArbitrary;
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
 * FixtureMonkey fixtureMonkey = FixtureMonkeySupplier.supplierDefault.get();
 * RequestUser requestCreateGroup = fixtureMonkey.giveMeOne(RequestUser.class);
 * }
 * </pre>
 * <p>
 *
 * @author nuts
 * @since 2024. 05. 14
 */
public abstract class FixtureMonkeySupplier {

    static public Supplier<FixtureMonkey> supplierDefault = () -> FixtureMonkey.builder()
            .objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
            .defaultNotNull(true).nullableElement(false)
            .plugin(new JakartaValidationPlugin())
            .plugin(new JqwikPlugin()
                    .javaTypeArbitraryGenerator(new JavaTypeArbitraryGenerator() {
                        @Override
                        public StringArbitrary strings() {
                            return Arbitraries.strings().ofMinLength(3).ofMaxLength(15).alpha();
                        }

                        @Override
                        public IntegerArbitrary integers() {
                            return Arbitraries.integers().between(0, 10000);
                        }

                        @Override
                        public LongArbitrary longs() {
                            return Arbitraries.longs().between(0, 10000);
                        }
                    }))
            .build();

    static public Supplier<FixtureMonkey> supplierSetterObjectIntrospect = () -> FixtureMonkey.builder()
            .objectIntrospector(BeanArbitraryIntrospector.INSTANCE)
            .defaultNotNull(true)
            .plugin(new JakartaValidationPlugin())
            .plugin(new JqwikPlugin()
                    .javaTypeArbitraryGenerator(new JavaTypeArbitraryGenerator() {
                        @Override
                        public StringArbitrary strings() {
                            return Arbitraries.strings().ofMinLength(3).ofMaxLength(15).alpha();
                        }

                        @Override
                        public IntegerArbitrary integers() {
                            return Arbitraries.integers().between(0, 10000);
                        }

                        @Override
                        public LongArbitrary longs() {
                            return Arbitraries.longs().between(0, 10000);
                        }
                    }))
            .build();

    static public Supplier<FixtureMonkey> supplierFieldReflection = () -> FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .defaultNotNull(true).nullableElement(false)
//            .plugin(new JakartaValidationPlugin())
            .plugin(new JqwikPlugin()
                    .javaTypeArbitraryGenerator(new JavaTypeArbitraryGenerator() {
                        @Override
                        public StringArbitrary strings() {
                            return Arbitraries.strings().ofMinLength(3).ofMaxLength(15).alpha();
                        }

                        @Override
                        public IntegerArbitrary integers() {
                            return Arbitraries.integers().between(0, 10000);
                        }

                        @Override
                        public LongArbitrary longs() {
                            return Arbitraries.longs().between(0, 10000);
                        }

                    }))
            .build();
}
