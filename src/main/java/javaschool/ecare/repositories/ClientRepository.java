package javaschool.ecare.repositories;

import javaschool.ecare.entities.Client;
import javaschool.ecare.entities.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findClientByContract(Contract contract);
    Optional<Client> findClientByIdClient(Long id);
    Optional<Client> findClientByEmail(String email);
    Client getClientByEmail(String email);

}
