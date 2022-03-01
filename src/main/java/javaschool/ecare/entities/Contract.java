package javaschool.ecare.entities;

import lombok.Data;

import javax.persistence.*;

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
    private Client client;

    public Contract() {

    }



}