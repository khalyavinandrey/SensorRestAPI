package restAPI.dto;

import restAPI.models.Sensor;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeasurementDTO {

    @NotNull
    @Column(name = "value")
    @Min(value = -100)
    @Max(value = 100)
    private Double value;

    @NotNull
    @Column(name = "raining")
    private Boolean isRaining;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sensor", referencedColumnName = "name")
    private Sensor sensor;
}
