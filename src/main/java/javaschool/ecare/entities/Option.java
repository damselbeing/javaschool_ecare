package javaschool.ecare.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "tariffs")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, name = "id")
    private Long idOption;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "price")
    private double price;

    @Column(nullable = false, name = "connection_cost")
    private double connectionCost;

    @ManyToMany
    @ToString.Exclude
    private Set<Tariff> tariffs;

    @ManyToMany
    @ToString.Exclude
    @JoinTable(
            name = "contracts_options",
            joinColumns = {@JoinColumn(name = "option_id")},
            inverseJoinColumns = {@JoinColumn(name = "contract_id")}
    )
    private Set<Contract> contracts;

    public Option() {

    }



}