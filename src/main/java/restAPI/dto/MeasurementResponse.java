package restAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class MeasurementResponse {
    private List<MeasurementDTO> measurementDTOS;
}
