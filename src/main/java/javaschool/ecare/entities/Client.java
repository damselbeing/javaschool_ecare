package javaschool.ecare.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "clients")
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    @OneToOne(mappedBy = "client")
    private Contract contract;

    public Client() {

    }

//    public Client(String name,
//                  String lastName,
//                  LocalDate birthDate,
//                  String passport,
//                  String address,
//                  String email,
//                  String password) {
//        this.name = name;
//        this.lastName = lastName;
//        this.birthDate = birthDate;
//        this.passport = passport;
//        this.address = address;
//        this.email = email;
//        this.password = password;
//    }




}