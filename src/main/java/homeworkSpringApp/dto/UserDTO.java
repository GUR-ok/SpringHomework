package homeworkSpringApp.dto;

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

    public static UserDTO from(User user) {
        UserDTO dto = new UserDTO();
        dto.setUuid(user.getUuid());
        dto.setName(user.getName());
        return dto;
    }

}
