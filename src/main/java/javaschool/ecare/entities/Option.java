package javaschool.ecare.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, name = "option_id")
    private Long idOption;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "price")
    private double price;

    @Column(nullable = false, name = "connection_cost")
    private double connectionCost;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "options")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Tariff> tariffs;

    @ManyToMany
    @JoinTable(
            name = "contracts_options",
            joinColumns = {@JoinColumn(name = "option_id")},
            inverseJoinColumns = {@JoinColumn(name = "contract_id")}
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Contract> contracts;

    public Option() {

    }



}