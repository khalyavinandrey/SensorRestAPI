package restAPI.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorDTO {

    @NotNull
    @Size(min = 3, max = 30, message = "incorrect length of name")
    private String name;
}
