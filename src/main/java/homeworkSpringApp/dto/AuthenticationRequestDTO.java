package homeworkSpringApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import homeworkSpringApp.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.xml.bind.annotation.*;

@Data
@NoArgsConstructor
public class AuthenticationRequestDTO {

    private String username;

    private String password;

    @Autowired
    @JsonIgnore
    private BCryptPasswordEncoder passwordEncoder;

    public static AuthenticationRequestDTO from(User person) {
        AuthenticationRequestDTO dto = new AuthenticationRequestDTO();
        dto.setUsername(person.getName());
        dto.setPassword(person.getPassword());
        return dto;
    }

    public User toPerson() {
        User person = new User();
        person.setName(this.getUsername());
        person.setPassword(passwordEncoder.encode(this.getPassword()));
        return person;
    }

    public AuthenticationRequestDTO(String username, String password) {
        this.password = password;
        this.username = username;
    }

}
