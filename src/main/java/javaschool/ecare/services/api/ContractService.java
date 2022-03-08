package javaschool.ecare.services.api;

import javaschool.ecare.dto.ContractDto;
import javaschool.ecare.exceptions.ClientNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ContractService {

    List<ContractDto> getContracts();

    ContractDto findContractByIdContract(Long id) throws ClientNotFoundException;
}
