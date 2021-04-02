package homeworkSpringApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import homeworkSpringApp.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private UUID uuid;
    private String name;
    @JsonIgnore
    private String password;

    public static UserDTO from(User user) {
        UserDTO dto = new UserDTO();
        dto.setUuid(user.getUuid());
        dto.setName(user.getName());
        return dto;
    }

    public User toUser() {
        User user = new User();
        user.setUuid(this.uuid);
        user.setName(this.name);
        user.setPassword(this.password);
        return user;
    }

}
