package restAPI.repositories;

import restAPI.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

    @Query("SELECT COUNT(*) FROM Measurement WHERE isRaining = true")
    Long getCounterOfRainingDays();
}
