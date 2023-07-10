package restAPI.controllers;

import restAPI.dto.MeasurementDTO;
import restAPI.dto.MeasurementResponse;
import restAPI.models.Measurement;
import restAPI.services.MeasurementService;
import restAPI.util.MeasurementErrorResponse;
import restAPI.util.MeasurementNotImportedException;
import restAPI.util.SensorErrorResponse;
import restAPI.util.SensorNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
@RequiredArgsConstructor
public class MeasurementsController {

    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;

    @GetMapping
    public MeasurementResponse getAllMeasurements() {
        return new MeasurementResponse(measurementService.findAll().stream().
                map((element) -> modelMapper.map(element, MeasurementDTO.class)).
                collect(Collectors.toList()));
    }

    @GetMapping("/rainyDaysCount")
    public Long countRainyDays() {
        return measurementService.getCounterOfRainyDays();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> importMeasurements(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            fieldErrors.forEach(fieldError -> {
                errorMessage.append(fieldError.getField())
                        .append(" - ")
                        .append(fieldError.getDefaultMessage())
                        .append(";");
            });

            throw new MeasurementNotImportedException(errorMessage.toString());
        }

        measurementService.importMeasurement(convertToModel(measurementDTO));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Measurement convertToModel(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleExceptions(MeasurementNotImportedException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e) {
        SensorErrorResponse sensorErrorResponse = new SensorErrorResponse(
                "There is no such sensors",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(sensorErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
