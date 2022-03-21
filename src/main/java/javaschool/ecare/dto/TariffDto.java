package javaschool.ecare.dto;

import javaschool.ecare.entities.Client;
import javaschool.ecare.entities.Contract;
import javaschool.ecare.entities.Option;
import javaschool.ecare.entities.Tariff;
import lombok.Data;

import java.io.Serializable;
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
