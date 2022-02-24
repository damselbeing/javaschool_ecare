package javaschool.ecare.converters;

import javaschool.ecare.dto.ContractDto;
import javaschool.ecare.entities.Contract;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class ContractConverter {

    public ContractDto entityToDto(Contract contract) {

        ContractDto dto = new ContractDto();
        dto.setIdContract(contract.getIdContract());
        dto.setNumber(contract.getNumber());
        dto.setBlockedByClient(contract.isBlockedByClient());
        dto.setBlockedByAdmin(contract.isBlockedByAdmin());
        dto.setClient(contract.getClient());

        return dto;
    }

    public List<ContractDto> entityToDto(List<Contract> contract) {

        if(contract == null) {
            return Collections.emptyList();
        } else {
            return contract.stream()
                    .map(x -> entityToDto(x))
                    .collect(Collectors.toList());
        }

    }

    public Contract dtoToEntity(ContractDto dto) {

        Contract contract = new Contract();
        contract.setIdContract(dto.getIdContract());
        contract.setNumber(dto.getNumber());
        contract.setBlockedByClient(dto.isBlockedByClient());
        contract.setBlockedByAdmin(dto.isBlockedByAdmin());
        contract.setClient(dto.getClient());

        return contract;
    }

    public List<Contract> dtoToEntity(List<ContractDto> dto) {

        if(dto == null) {
            return Collections.emptyList();
        } else {
            return dto.stream()
                    .map(x -> dtoToEntity(x))
                    .collect(Collectors.toList());
        }
    }


}
