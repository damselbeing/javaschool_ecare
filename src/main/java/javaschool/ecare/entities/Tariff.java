package javaschool.ecare.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
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
    @Column(nullable = false, name = "tariff_id")
    private Long idTariff;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "price")
    private double price;

    @Column(nullable = false, name = "archived")
    private boolean archived = false;

    @ManyToMany
    @JoinTable(
            name = "options_tariffs",
            joinColumns = {@JoinColumn(name = "tariff_id")},
            inverseJoinColumns = {@JoinColumn(name = "option_id")}
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Option> options = new HashSet<>();

    @OneToMany(mappedBy = "tariff")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Contract> contracts;

    public Tariff() {

    }



}