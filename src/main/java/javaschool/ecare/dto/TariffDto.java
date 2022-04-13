package javaschool.ecare.dto;

import javaschool.ecare.entities.Contract;
import javaschool.ecare.entities.Option;
import lombok.Data;


import java.util.Set;

@Data
public class TariffDto {
    private Long idTariff;
    private String name;
    private double price;
    private boolean archived;
    private boolean markedForUpdate;
    private Set<Option> options;
    private Set<Contract> contracts;
}
