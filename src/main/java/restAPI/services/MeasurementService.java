package restAPI.services;

import restAPI.models.Measurement;
import restAPI.repositories.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    @Transactional
    public void importMeasurement(Measurement measurement) {
        enrichMeasurement(measurement);
        measurementRepository.save(measurement);
    }

    public void enrichMeasurement(Measurement measurement) {
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()));
        measurement.setMeasuredAt(new Date());
    }

    public Long getCounterOfRainyDays() {
        return measurementRepository.getCounterOfRainingDays();
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }
}
