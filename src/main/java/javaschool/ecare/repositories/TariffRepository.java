package javaschool.ecare.repositories;

import javaschool.ecare.entities.Contract;
import javaschool.ecare.entities.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Long> {

    Optional<Tariff> findTariffByIdTariff(Long id);


}
