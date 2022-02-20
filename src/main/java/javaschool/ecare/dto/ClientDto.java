package javaschool.ecare.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class ClientDto implements Serializable {
    private Long id;
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private String passport;
    private String address;
    private String email;
    private String password;
}
