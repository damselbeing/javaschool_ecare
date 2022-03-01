package javaschool.ecare.repositories;

import javaschool.ecare.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findClientByPassport(String passport);
    Optional<Client> findClientByContract(String contract);
}
