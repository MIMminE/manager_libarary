package nuts.lib.manager.fixture_manager.target;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class RequestObject {

    @Size(min = 10, max = 30)
    String adminName;

    @Size(min = 10, max = 30)
    String adminId;

    @NotNull
    @Size(min = 5, max = 15)
    String password;

    @NotNull
    Address address;
}