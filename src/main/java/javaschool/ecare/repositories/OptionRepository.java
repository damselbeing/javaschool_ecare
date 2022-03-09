package javaschool.ecare.repositories;

import javaschool.ecare.entities.Contract;
import javaschool.ecare.entities.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {



}
