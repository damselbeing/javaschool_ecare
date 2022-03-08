package javaschool.ecare.repositories;

import javaschool.ecare.entities.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    Optional<Contract> findContractByNumber(String number);
    Optional<Contract> findContractByIdContract(Long id);

}
