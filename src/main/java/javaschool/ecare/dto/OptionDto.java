package javaschool.ecare.dto;

import javaschool.ecare.entities.Client;
import javaschool.ecare.entities.Contract;
import javaschool.ecare.entities.Option;
import javaschool.ecare.entities.Tariff;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class OptionDto {
    private Long idOption;
    private String name;
    private double price;
    private double connectionCost;
    private Set<Tariff> tariffs;
    private Set<Contract> contracts;
}
