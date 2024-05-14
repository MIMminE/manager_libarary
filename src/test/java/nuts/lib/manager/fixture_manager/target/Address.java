package nuts.lib.manager.fixture_manager.target;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@AllArgsConstructor
@Builder
public class Address {
    @NotBlank
    private String city;
    @NotBlank
    private String postal;
}
