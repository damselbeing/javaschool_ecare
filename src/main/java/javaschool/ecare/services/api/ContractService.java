package javaschool.ecare.services.api;

import javaschool.ecare.dto.ContractDto;
import javaschool.ecare.exceptions.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

public interface ContractService {

    List<ContractDto> getContracts();

    ContractDto findContractByIdContract(Long id) throws ContractNotFoundException;

    void blockByAdmin(Long id) throws ContractNotFoundException;

    void unblockByAdmin(Long id) throws ContractNotFoundException;

    void updateContractOptions(Long id, String[] options) throws ContractNotFoundException, OptionNotFoundException, NotValidOptionsException;

    void blockByClient(Long id) throws ContractNotFoundException;

    void unblockByClient(Long id) throws ContractNotFoundException;

    Set<String> getGeneratedNumbers();

    String generateNumber();

    void updateContractTariff(Long idContract, String idTariff) throws ContractNotFoundException, TariffNotFoundException, IOException, TimeoutException;

    void addNewContract(ContractDto dto, Long id) throws ClientNotFoundException, ContractNotFoundException;
}
