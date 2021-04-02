package homeworkSpringApp.controller;

import homeworkSpringApp.dto.CarDTO;
import homeworkSpringApp.dto.CarListDTO;
import homeworkSpringApp.model.Car;
import homeworkSpringApp.model.CarList;
import homeworkSpringApp.model.User;
import homeworkSpringApp.security.AuthService;
import homeworkSpringApp.service.ListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.simple.parser.ParseException;
import java.util.*;

/*   Author: GORELOV YURIY
*
*        + POST /lists - создание нового списка
*        + GET /lists - отображает все имеющиеся списки (с краткой информацией по ним) для данного пользователя
*        + GET /lists/{id} - Пользователь может получить более подробную информацию по каждому списку по id этого списка (вывод всех элементов списка)
*        + POST /lists/{id}/elements - добавление элемента(из тела запроса) в конец списка с заданным идентификатором
*        + DELETE /lists/{id}/elements/{id_element} - удаление элеиента по его id из списка с заданным идентификатором
*        + GET /lists/{id}/elements/{id_element} - возвращает элемент из списка
*        + GET /lists/{id}/size - размер списка
*        + PUT /lists/{id}/elements - добавить список элементов
*        + GET /lists/{id}/find?element={json_element} - возвращает, сколько раз элемент встречается в списке
*        Для sort и shuffle методы
*        + GET /lists/{id}/sort - возвращает отсортированный список
*        в качестве сортировки можно сразу же определить, по какому компаратору будет происходить сортировка
*        + GET /lists/{id}/shuffle - возвращает "перемешанный" список
*/

@RestController
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class UserController {

    private final ListService service;
    private final AuthService authService;

    //Создание нового юзера
    @PostMapping("/admin/create")
    public ResponseEntity<String> addUser( @RequestBody User user) {
        log.info("addUser through controller");
        service.createUser(user);
        return ResponseEntity.ok(user.getUuid().toString());
    }

    //Удаление юзера
    @PostMapping("/admin/delete/{uuid}")
    public ResponseEntity<String> deleteUser( @PathVariable UUID uuid) {
        log.info("delete through controller");
        service.deleteUser(uuid);
        return ResponseEntity.ok().build();
    }

    //Создание нового списка
    @PostMapping("/lists")
    public ResponseEntity<String> addList(@RequestBody CarList list, @RequestHeader (name="Authorization") String token) {
        log.info("addUserList through controller");
        service.addCarList(list, authService.getUserUuidByToken(token));
        return ResponseEntity.ok("Added list id: " + list.getId());
    }

    //Удаление списка по id
    @DeleteMapping("/lists/{id}")
    public ResponseEntity deleteList(@PathVariable long id, @RequestHeader (name="Authorization") String token) {
        log.info("deleteUserList through controller");
        service.deleteList(id, authService.getUserUuidByToken(token));
        return ResponseEntity.ok().build();
    }

    //Получение всех списков пользователя по uuid
    @GetMapping("/lists")
    public ResponseEntity<List<CarListDTO>> getUserLists(@RequestHeader (name="Authorization") String token) {
        log.info("getLists through controller");
        //Получение имени юзера из токена, по имени юзера получение его UUID и отправка в сервис
        return service.getLists(authService.getUserUuidByToken(token));
    }

    //Получение подробной информации по списку
    @GetMapping("/lists/{id}")
    public ResponseEntity<String> getListInformation(@PathVariable long id, @RequestHeader (name="Authorization") String token) {
        log.info("getListInformation through controller");
        return service.getCarList(id, authService.getUserUuidByToken(token));
    }

    //Добавление элемента в список
    @PostMapping("/lists/{id}/elements")
    public ResponseEntity addElementToList(@PathVariable long id, @RequestBody Car car, @RequestHeader (name="Authorization") String token) {
        log.info("addElementToList through controller");
        return service.addCarToList(id, car, authService.getUserUuidByToken(token));
    }

    //Удаление элемента списка
    @DeleteMapping("/lists/{id}/elements/{id_element}")
    public ResponseEntity deleteElement(@PathVariable long id, @PathVariable int id_element, @RequestHeader (name="Authorization") String token) {
        log.info("deleteElementInList through controller");
        return service.deleteEelementFromList(id, id_element, authService.getUserUuidByToken(token));
    }

    //Получение элемента списка
    @GetMapping("/lists/{id}/elements/{id_element}")
    public ResponseEntity<CarDTO> getElement(@PathVariable long id, @PathVariable int id_element, @RequestHeader (name="Authorization") String token) {
        log.info("getElementInList through controller");
        return service.getCarFromList(id, id_element, authService.getUserUuidByToken(token));
    }

    //Получение размера списка
    @GetMapping("/lists/{id}/size")
    public ResponseEntity<Integer> getListSize(@PathVariable long id, @RequestHeader (name="Authorization") String token) {
        log.info("getListSize through controller");
        return service.getListSize(id, authService.getUserUuidByToken(token));
    }

    //Добавить список элементов
    @PutMapping("/lists/{id}/elements")
    public ResponseEntity<String> addListToList(@PathVariable long id, @RequestBody CarList list, @RequestHeader (name="Authorization") String token) {
        log.info("putElementsToList through controller");
        return service.addListToList(id, list, authService.getUserUuidByToken(token));
    }

    //Сколько раз элемент встречается в списке
    @GetMapping("/lists/{id}/find")
    public ResponseEntity<Integer> countElement(@PathVariable long id,
                                                @RequestParam(value="element", required=true) String carjson,
                                                @RequestHeader (name="Authorization") String token) throws ParseException {
        log.info("countElementsInList through controller");
        return service.countElement(id, carjson, authService.getUserUuidByToken(token));
    }


    //Возвращает отсортированный список
    @GetMapping("/lists/{id}/sort")
    public ResponseEntity<String> sortList(@PathVariable long id,
                                            @RequestHeader (name="Authorization") String token,
                                            @RequestParam (name="comparator") String comparatorName) {
        log.info("get sorted list through controller");
        return service.getSortedList(id, comparatorName, authService.getUserUuidByToken(token));
    }

    //Возвращает перемешанный список
    @GetMapping("/lists/{id}/shuffle")
    public ResponseEntity<String> shuffleList(@PathVariable long id,
                                           @RequestHeader (name="Authorization") String token) {
        log.info("get sorted list through controller");
        return service.getShuffledList(id, authService.getUserUuidByToken(token));
    }

}
