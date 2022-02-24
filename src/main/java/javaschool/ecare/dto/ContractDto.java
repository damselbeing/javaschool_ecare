package javaschool.ecare.dto;

import javaschool.ecare.entities.Client;
import lombok.Data;

import java.io.Serializable;

@Data
public class ContractDto implements Serializable {
    private Long idContract;
    private String number;
    private boolean blockedByClient;
    private boolean blockedByAdmin;
    private Client client;
}
