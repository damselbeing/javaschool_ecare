package javaschool.ecare.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Data
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, name = "client_id")
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

    @Transient
    private String passwordConfirm;

    @Column(name = "role")
    private String role;

    @OneToOne
    @JoinColumn(name = "contract_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Contract contract;



    public Client() {

    }

    public Client(Long idClient, String name, String lastName, LocalDate birthDate, String passport, String address, String email, String password) {
        this.idClient = idClient;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.passport = passport;
        this.address = address;
        this.email = email;
        this.password = password;
    }

}