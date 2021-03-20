package homeworkSpringApp.dto;

import homeworkSpringApp.model.NumberList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NumberListDTO {

    private Long id;
    private String shortDescription;
    private String arrayAsString;
    private UUID uuid;

    public static NumberListDTO from(NumberList numberList) {
        NumberListDTO dto = new NumberListDTO();
        dto.setId(numberList.getId());
        dto.setShortDescription(numberList.getShortDescription());
        dto.setArrayAsString(numberList.getNumlist().toString());
        dto.setUuid(numberList.getUser().getUuid());
        return dto;
    }

}
