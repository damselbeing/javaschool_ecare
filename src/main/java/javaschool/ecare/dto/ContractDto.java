package javaschool.ecare.dto;

import javaschool.ecare.entities.Client;
import javaschool.ecare.entities.Option;
import javaschool.ecare.entities.Tariff;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class ContractDto implements Serializable {
    private Long idContract;
    private String number;
    private boolean blockedByClient;
    private boolean blockedByAdmin;
    private Client client;
    private Tariff tariff;
    private Set<Option> options;
}
