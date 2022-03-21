package javaschool.ecare.services.api;

import javaschool.ecare.dto.ContractDto;
import javaschool.ecare.exceptions.*;

import java.util.List;
import java.util.Set;

public interface ContractService {

    List<ContractDto> getContracts();

    ContractDto findContractByIdContract(Long id) throws ClientNotFoundException;

    void blockByAdmin(Long id) throws ClientNotFoundException;

    void unblockByAdmin(Long id) throws ClientNotFoundException;

    void updateContractOptions(Long id, String[] options) throws ClientNotFoundException, OptionNotFoundException, NotValidOptionsException;

    void blockByClient(Long id) throws ClientNotFoundException;

    void unblockByClient(Long id) throws ClientNotFoundException;

    Set<String> getGeneratedNumbers();

    void updateContractTariff(Long idContract, String idTariff) throws ClientNotFoundException, TariffNotFoundException;

    void addNewContract(ContractDto dto, Long id) throws ClientNotFoundException, ContractNotFoundException;
}
