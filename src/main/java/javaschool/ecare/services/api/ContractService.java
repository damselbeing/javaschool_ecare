package javaschool.ecare.services.api;

import javaschool.ecare.dto.ContractDto;
import javaschool.ecare.exceptions.ClientNotFoundException;
import javaschool.ecare.exceptions.NotValidOptionsException;
import javaschool.ecare.exceptions.OptionNotFoundException;
import javaschool.ecare.exceptions.TariffNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface ContractService {

    List<ContractDto> getContracts();

    ContractDto findContractByIdContract(Long id) throws ClientNotFoundException;

    void blockByAdmin(Long id) throws ClientNotFoundException;

    void unblockByAdmin(Long id) throws ClientNotFoundException;

    void updateContract(Long id, String[] options) throws ClientNotFoundException, OptionNotFoundException, NotValidOptionsException;

    void blockByClient(Long id) throws ClientNotFoundException;

    void unblockByClient(Long id) throws ClientNotFoundException;

    Set<String> getGeneratedNumbers();

    void updateTariff(Long idContract, String idTariff) throws ClientNotFoundException, TariffNotFoundException;
}
