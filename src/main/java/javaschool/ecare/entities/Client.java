package javaschool.ecare.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, name = "id")
    private Long idClient;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @Column(nullable = false, name = "birth_date")
    private LocalDate birthDate;

    @Column(nullable = false, name = "passport")
    private String passport;

    @Column(nullable = false, name = "address")
    private String address;

    @Column(nullable = false, name = "e_mail")
    private String email;

    @Column(nullable = false, name = "password")
    private String password;

    @OneToOne
    @JoinColumn(nullable = false, name = "contract_id")
    @ToString.Exclude
    private Contract contract;

    public Client() {

    }

}