package homeworkSpringApp.dto;

import homeworkSpringApp.model.CarList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarListDTO {

    private Long id;
    private String shortDescription;
    private String arrayAsString;
    private UUID ownerUuid;

    public static CarListDTO from(CarList carList) {
        CarListDTO dto = new CarListDTO();
        dto.setId(carList.getId());
        dto.setShortDescription(carList.getShortDescription());
        dto.setArrayAsString(carList.getCarList().toString());
        dto.setOwnerUuid(carList.getUser().getUuid());
        return dto;
    }

    public CarList toCarlist() {
        CarList carList = new CarList();
        carList.setId(this.id);
        carList.setShortDescription(this.shortDescription);
        return carList;
    }

}
