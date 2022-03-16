package javaschool.ecare.services.api;

import javaschool.ecare.dto.ContractDto;
import javaschool.ecare.exceptions.ClientNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ContractService {

    List<ContractDto> getContracts();

    ContractDto findContractByIdContract(Long id) throws ClientNotFoundException;

    void blockByAdmin(Long id) throws ClientNotFoundException;

    void unblockByAdmin(Long id) throws ClientNotFoundException;

    void updateContract(Long id, String[] options) throws ClientNotFoundException;
}
