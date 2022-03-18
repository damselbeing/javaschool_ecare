package javaschool.ecare.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
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

    @ManyToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinTable(
            name = "conflicting_options",
            joinColumns = {@JoinColumn(name = "option_id")},
            inverseJoinColumns = {@JoinColumn(name = "conflicting_option_id")}
    )
    private Set<Option> conflictingOptions;

    @ManyToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinTable(
            name = "additional_options",
            joinColumns = {@JoinColumn(name = "option_id")},
            inverseJoinColumns = {@JoinColumn(name = "additional_option_id")}
    )
    private Set<Option> additionalOptions;

    @ManyToMany(mappedBy = "options")
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