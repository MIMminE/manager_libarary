package nuts.lib.manager.fixture_manager;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FixtureGenerateSupportTest extends FixtureGenerateSupport{

    @RepeatedTest(100)
    void test() {
        // given
        FixtureSampleClass fixtureSampleClass = getOrderedObject(FixtureSampleClass.class).get(0);

        // when
        System.out.println(fixtureSampleClass);

        // then
    }





    @Override
    protected List<OrderSheet> ordersObject() {
        return List.of(
                OrderSheet.order(FixtureSampleClass.class, 1)
        );
    }

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