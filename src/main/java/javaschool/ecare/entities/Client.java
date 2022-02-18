package javaschool.ecare.entities;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "clientdb")
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    @SequenceGenerator(
//            name = "client_sequence",
//            sequenceName = "client_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "client_sequence"
//    )
    private Long id;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "last_name")
    private String lastName;

    @NonNull
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @NonNull
    @Column(name = "passport")
    private String passport;

    @NonNull
    @Column(name = "address")
    private String address;

    @NonNull
    @Column(name = "e_mail")
    private String email;

    @NonNull
    @Column(name = "password")
    private String password;

    public Client() {

    }

    public Client(String name,
                  String lastName,
                  LocalDate birthDate,
                  String passport,
                  String address,
                  String email,
                  String password) {
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.passport = passport;
        this.address = address;
        this.email = email;
        this.password = password;
    }




}