package restAPI.services;

import restAPI.models.Sensor;
import restAPI.repositories.SensorRepository;
import restAPI.util.SensorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;

    public Sensor findByName(String name) {
        return sensorRepository.findByName(name).orElseThrow(SensorNotFoundException::new);
    }

    @Transactional
    public void registerSensor(Sensor sensor) {
        sensorRepository.save(sensor);
    }
}
