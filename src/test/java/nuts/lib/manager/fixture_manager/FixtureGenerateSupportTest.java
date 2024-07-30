package nuts.lib.manager.fixture_manager;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;

import static nuts.lib.manager.fixture_manager.FixtureManager.orderCustom;

class FixtureGenerateSupportTest {

    static FixtureManager fixtureManager;

    @BeforeAll
    static void setUp() {
        fixtureManager = new FixtureManager(List.of(
                OrderSheet.order(
                    orderCustom(FixtureSampleClass.class)
                            .minSize("requestId", 3), 1)));
    }

    @RepeatedTest(100)
    void test() {
        // given

//        FixtureSampleClass fixtureSampleClass = fixtureManager.getOrderObject(FixtureSampleClass.class);
        FixtureSampleClass fixtureSampleClass = fixtureManager.getOrderObject(FixtureSampleClass.class);

        // when
        System.out.println(fixtureSampleClass);

        // then
    }



    @Getter
    @ToString
    static class FixtureSampleClass {
        @Size(min = 5)
        private String requestId;

        @Size(min = 2)
        private String newName;

        @Size(min = 5)
        private String newPassword;

        @Pattern(regexp = "^010-\\d{4}-\\d{4}$")
        private String newContactNumber;

        @NotBlank
        private String newCorporationId;
    }
}