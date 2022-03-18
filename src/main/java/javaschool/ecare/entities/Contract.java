package javaschool.ecare.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "contracts")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, name = "contract_id")
    private Long idContract;

    @Column(name = "number")
    private String number;

    @Column(nullable = false, name = "blocked_by_client")
    private boolean blockedByClient = false;

    @Column(nullable = false, name = "blocked_by_admin")
    private boolean blockedByAdmin = false;

    @OneToOne(mappedBy = "contract")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Client client;

    @ManyToOne
    @JoinColumn(name = "tariff_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Tariff tariff;

    @ManyToMany(mappedBy = "contracts")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Option> contractOptions;

    public Contract() {

    }



}