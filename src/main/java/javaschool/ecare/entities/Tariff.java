package javaschool.ecare.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "tariffs")
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, name = "id")
    private Long idTariff;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "price")
    private double price;

    @ManyToMany
    @ToString.Exclude
    @JoinTable(
            name = "options_tariffs",
            joinColumns = {@JoinColumn(name = "tariff_id")},
            inverseJoinColumns = {@JoinColumn(name = "option_id")}
    )
    private Set<Option> options;

    @OneToMany(mappedBy = "tariff", fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<Contract> contracts;

    public Tariff() {

    }



}