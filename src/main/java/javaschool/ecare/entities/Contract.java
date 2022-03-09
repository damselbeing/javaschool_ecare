package javaschool.ecare.entities;

import lombok.Data;
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
    @Column(nullable = false, name = "id")
    private Long idContract;

    @Column(nullable = false, name = "number")
    private String number;

    @Column(nullable = false, name = "blocked_by_client")
    private boolean blockedByClient = false;

    @Column(nullable = false, name = "blocked_by_admin")
    private boolean blockedByAdmin = false;

    @OneToOne(mappedBy = "contract")
    @ToString.Exclude
    private Client client;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;

    @ManyToMany
    @ToString.Exclude
    private Set<Option> options;

    public Contract() {

    }



}