package javaschool.ecare.dto;

import javaschool.ecare.entities.Contract;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class ClientDto {
    private Long idClient;
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private String passport;
    private String address;
    private String email;
    private String password;
    private String passwordConfirm;
    private Contract contract;
}
